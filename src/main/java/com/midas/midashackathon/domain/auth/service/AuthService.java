package com.midas.midashackathon.domain.auth.service;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.department.exception.InvalidDepartmentException;
import com.midas.midashackathon.domain.department.repository.DepartmentRepository;
import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.user.exception.UserAlreadyExistsException;
import com.midas.midashackathon.domain.user.exception.UserNotFoundException;
import com.midas.midashackathon.domain.user.presentation.dto.request.SignInRequest;
import com.midas.midashackathon.domain.user.presentation.dto.request.SignUpRequest;
import com.midas.midashackathon.domain.user.presentation.dto.response.SignInResponse;
import com.midas.midashackathon.domain.user.repository.UserRepository;
import com.midas.midashackathon.domain.user.type.Role;
import com.midas.midashackathon.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {
        if(userRepository.findByAccountId(request.getId()).isPresent())
            throw UserAlreadyExistsException.EXCEPTION;

        DepartmentEntity department = request.getIsAdmin() ? null : departmentRepository.findById(request.getDepartment())
                .orElseThrow(() -> InvalidDepartmentException.EXCEPTION);

        UserEntity user = UserEntity.builder()
                .accountId(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .department(department)
                .role(request.getIsAdmin() ? Role.ADMIN : Role.NORMAL)
                .build();

        user = userRepository.save(user);
        if(!request.getIsAdmin()) department.addMember(user);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest request) {
        UserEntity user = userRepository.findByAccountId(request.getId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw UserNotFoundException.EXCEPTION;

        return SignInResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(user.getId()))
                .role(user.getRole())
                .build();
    }
}

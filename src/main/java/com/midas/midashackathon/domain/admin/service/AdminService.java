package com.midas.midashackathon.domain.admin.service;

import com.midas.midashackathon.domain.admin.presentation.dto.request.UserEditRequest;
import com.midas.midashackathon.domain.admin.presentation.dto.response.DepartmentInfo;
import com.midas.midashackathon.domain.admin.presentation.dto.response.UserListResponse;
import com.midas.midashackathon.domain.admin.presentation.dto.response.UserResponse;
import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.department.exception.DepartmentNotFoundException;
import com.midas.midashackathon.domain.department.repository.DepartmentRepository;
import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.user.exception.UserNotFoundException;
import com.midas.midashackathon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public UserListResponse getAllUser() {
        List<UserEntity> users = userRepository.findAll();

        return UserListResponse.builder()
                .userList(users.stream().map(it -> UserResponse.builder()
                        .id(it.getId())
                        .accountId(it.getAccountId())
                        .phoneNumber(it.getPhoneNumber())
                        .isAdmin("ADMIN".equals(it.getRole().name()))
                        .departmentInfo(DepartmentInfo.builder()
                                .code(it.getDepartment().getCode())
                                .name(it.getName())
                                .build())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void modifyUser(Long userId, UserEditRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        DepartmentEntity department = departmentRepository.findById(request.getDepartment())
                .orElseThrow(() -> DepartmentNotFoundException.EXCEPTION);

        user.updateUser(request.getName(), department, request.getIsAdmin());
    }
}

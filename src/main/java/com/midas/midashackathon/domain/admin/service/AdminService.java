package com.midas.midashackathon.domain.admin.service;

import com.midas.midashackathon.domain.admin.presentation.dto.request.UserEditRequest;
import com.midas.midashackathon.domain.admin.presentation.dto.response.*;
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

    @Transactional
    public ViolationListResponse getCurrentViolations() {
        List<DepartmentEntity> departments = departmentRepository.findAll();

        List<ViolationGroupResponse> groups = departments.stream().map(department -> ViolationGroupResponse.builder()
                        .departmentId(department.getCode())
                        .departmentName(department.getName())
                        .violationHistory(department.getMembers().stream().filter(it ->
                            it.getWorks().stream()
                                    .limit(5)
                                    .map(work -> work.getActualEnd().toLocalTime().getHour() - work.getActualStart().toLocalTime().getHour())
                                    .collect(Collectors.averagingDouble(hour -> hour)) < 8.0
                        ).map(it ->
                            ViolationResponse.builder()
                                    .userId(it.getId())
                                    .userName(it.getName())
                                    .workHourAverage(it.getWorks().stream()
                                    .limit(5)
                                    .map(work -> work.getActualEnd().toLocalTime().getHour() - work.getActualStart().toLocalTime().getHour())
                                    .collect(Collectors.averagingDouble(hour -> hour)))
                                    .build()
                                ).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return ViolationListResponse.builder()
                .violationList(groups)
                .build();
    }
}

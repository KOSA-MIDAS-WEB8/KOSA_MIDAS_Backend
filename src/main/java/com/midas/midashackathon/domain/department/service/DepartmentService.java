package com.midas.midashackathon.domain.department.service;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.department.exception.DepartmentNotFoundException;
import com.midas.midashackathon.domain.department.presentation.dto.request.DepartmentEditRequest;
import com.midas.midashackathon.domain.department.presentation.dto.response.DepartmentListResponse;
import com.midas.midashackathon.domain.department.presentation.dto.response.DepartmentResponse;
import com.midas.midashackathon.domain.department.presentation.dto.response.MemberListResponse;
import com.midas.midashackathon.domain.department.presentation.dto.response.MemberResponse;
import com.midas.midashackathon.domain.department.repository.DepartmentRepository;
import com.midas.midashackathon.global.utils.StringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.midas.midashackathon.global.utils.Formatters.HOUR_MINUTE_FORMATTER;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public DepartmentListResponse getAllDepartments() {
        List<DepartmentEntity> departments = departmentRepository.findAll();

        return DepartmentListResponse.builder()
                .departmentList(departments.stream().map(it -> DepartmentResponse.builder()
                                .code(it.getCode())
                                .name(it.getName())
                                .coreTimeStart(HOUR_MINUTE_FORMATTER.format(it.getCoreTimeStart().toInstant()))
                                .coreTimeHours(it.getRequiredCoreTime())
                                .workHour(it.getRequiredWorkTime())
                                .defaultStartHour(HOUR_MINUTE_FORMATTER.format(it.getDefaultStartTime().toInstant()))
                                .memberCount(it.getMembers().size())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void createDepartment(DepartmentEditRequest request) {
        DepartmentEntity department = DepartmentEntity.builder()
                .code(StringGenerator.generateUpper(6))
                .coreTimeStart(Time.valueOf(LocalTime.parse(request.getCoreTimeStart(), HOUR_MINUTE_FORMATTER)))
                .requiredCoreTime(request.getCoreTimeHours())
                .requiredWorkTime(request.getWorkHour())
                .members(new ArrayList<>())
                .build();

        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public MemberListResponse getAllMembersOfDepartment(String departmentCode) {
        DepartmentEntity department = departmentRepository.findById(departmentCode)
                .orElseThrow(() -> DepartmentNotFoundException.EXCEPTION);

        return MemberListResponse.builder()
                .members(department.getMembers().stream().map(it ->
                        MemberResponse.builder()
                                .userId(it.getId())
                                .name(it.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void editDepartment(String departmentCode, DepartmentEditRequest request) {
        DepartmentEntity department = departmentRepository.findById(departmentCode)
                .orElseThrow(() -> DepartmentNotFoundException.EXCEPTION);

        department.update(request.getName(), Time.valueOf(LocalTime.parse(request.getCoreTimeStart(), HOUR_MINUTE_FORMATTER)),
                request.getCoreTimeHours(), request.getWorkHour(), Time.valueOf(LocalTime.parse(request.getDefaultStartHour(), HOUR_MINUTE_FORMATTER)));
    }
}

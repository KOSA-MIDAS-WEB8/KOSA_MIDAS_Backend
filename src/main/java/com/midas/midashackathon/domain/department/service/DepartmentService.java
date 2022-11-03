package com.midas.midashackathon.domain.department.service;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.department.exception.DepartmentNotFoundException;
import com.midas.midashackathon.domain.department.presentation.dto.request.DepartmentEditRequest;
import com.midas.midashackathon.domain.department.presentation.dto.response.*;
import com.midas.midashackathon.domain.department.repository.DepartmentRepository;
import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import com.midas.midashackathon.domain.work.presentation.dto.response.TodoResponse;
import com.midas.midashackathon.global.utils.Formatters;
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
                                .activity(it.getTodoList().stream().collect(Collectors.groupingBy(grp -> grp.getConductAt().toLocalDate()))
                                        .entrySet().stream().map(entry -> ActivityResponse.builder()
                                                .date(Formatters.DATE_FORMATTER.format(entry.getKey()))
                                                .plan(it.getWorks().stream().filter(plan -> plan.getDate().isEqual(entry.getKey()))
                                                        .map(plan -> WorkCommonDto.builder()
                                                                .start(HOUR_MINUTE_FORMATTER.format(plan.getPlannedStart().toLocalTime()))
                                                                .end(HOUR_MINUTE_FORMATTER.format(plan.getPlannedEnd().toLocalTime()))
                                                                .build())
                                                        .collect(Collectors.toList()).get(0))
                                                .todoList(entry.getValue().stream().filter(todo -> !todo.isVisibleToTeam()).map(todo -> TodoResponse.builder()
                                                                .id(todo.getId())
                                                                .title(todo.getName())
                                                                .description(todo.getDescription())
                                                                .time(HOUR_MINUTE_FORMATTER.format(todo.getConductAt().toLocalTime()))
                                                                .visibleToTeam(null)
                                                                .status(todo.getStatus().toString())
                                                                .build())
                                                        .collect(Collectors.toList()))
                                                .build())
                                        .collect(Collectors.toList()))
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

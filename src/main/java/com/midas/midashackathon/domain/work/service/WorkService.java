package com.midas.midashackathon.domain.work.service;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.user.facade.UserFacade;
import com.midas.midashackathon.domain.work.entity.TodoEntity;
import com.midas.midashackathon.domain.work.entity.WorkEntity;
import com.midas.midashackathon.domain.work.exception.PlanNotQualifiedException;
import com.midas.midashackathon.domain.work.exception.TodoNotFoundException;
import com.midas.midashackathon.domain.work.exception.WorkClosedException;
import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import com.midas.midashackathon.domain.work.presentation.dto.request.EditTodoRequest;
import com.midas.midashackathon.domain.work.presentation.dto.request.TodoStatusRequest;
import com.midas.midashackathon.domain.work.presentation.dto.request.WorkStatusRequest;
import com.midas.midashackathon.domain.work.presentation.dto.response.TodoGroupResponse;
import com.midas.midashackathon.domain.work.presentation.dto.response.TodoListResponse;
import com.midas.midashackathon.domain.work.presentation.dto.response.TodoResponse;
import com.midas.midashackathon.domain.work.presentation.dto.response.WorkStatusResponse;
import com.midas.midashackathon.domain.work.repository.TodoRepository;
import com.midas.midashackathon.domain.work.repository.WorkRepository;
import com.midas.midashackathon.domain.work.type.TodoStatus;
import com.midas.midashackathon.global.utils.Formatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WorkService {
    private final WorkRepository workRepository;
    private final TodoRepository todoRepository;
    private final UserFacade userFacade;

    @Transactional
    public WorkEntity getWorkOrCreated(UserEntity user, LocalDate date) {
        DepartmentEntity department = user.getDepartment();

        WorkEntity work = workRepository.findByAuthorAndDate(user, date)
                .orElse(WorkEntity.builder()
                        .plannedStart(department.getDefaultStartTime())
                        .plannedEnd(Time.valueOf(department.getDefaultStartTime()
                                .toLocalTime().plusHours(department.getRequiredWorkTime())))
                        .author(user)
                        .build());

        if(work.getId() == null) workRepository.save(work);
        return work;
    }

    @Transactional
    public WorkStatusResponse getStatus(String date) {
        LocalDate queryDate = LocalDate.parse(date, Formatters.DATE_FORMATTER);
        WorkEntity work = getWorkOrCreated(userFacade.queryCurrentUser(), queryDate);

        return WorkStatusResponse.builder()
                .actual(WorkCommonDto.builder()
                        .start(Formatters.HOUR_MINUTE_FORMATTER.format(work.getActualStart().toInstant()))
                        .end(Formatters.HOUR_MINUTE_FORMATTER.format(work.getActualEnd().toInstant()))
                        .build())
                .plan(WorkCommonDto.builder()
                        .start(Formatters.HOUR_MINUTE_FORMATTER.format(work.getPlannedStart().toInstant()))
                        .end(Formatters.HOUR_MINUTE_FORMATTER.format(work.getPlannedEnd().toInstant()))
                        .build())
                .build();
    }

    @Transactional
    public void updatePlan(String date, WorkCommonDto request) {
        UserEntity user = userFacade.queryCurrentUser();
        DepartmentEntity department = user.getDepartment();
        LocalDate queryDate = LocalDate.parse(date, Formatters.DATE_FORMATTER);
        WorkEntity work = getWorkOrCreated(user, queryDate);

        LocalTime start = LocalTime.parse(request.getStart(), Formatters.HOUR_MINUTE_FORMATTER);
        LocalTime end = LocalTime.parse(request.getEnd(), Formatters.HOUR_MINUTE_FORMATTER);

        if(start.isBefore(department.getCoreTimeStart().toLocalTime()) &&
                end.isAfter(department.getCoreTimeStart().toLocalTime().plusHours(department.getRequiredCoreTime()))) {
            work.updatePlan(start, end);
        } else {
            throw PlanNotQualifiedException.EXCEPTION;
        }
    }

    @Transactional
    public void updateActual(String date, WorkStatusRequest request) {
        LocalDate queryDate = LocalDate.parse(date, Formatters.DATE_FORMATTER);
        WorkEntity work = getWorkOrCreated(userFacade.queryCurrentUser(), queryDate);

        if(!LocalDate.now().isEqual(queryDate))
            throw WorkClosedException.EXCEPTION;

        if(work.getActualStart() == null && "START".equals(request.getStatus())) {
            work.setActualStart(Time.valueOf(LocalTime.now()));
        }else if(work.getActualStart() != null && work.getActualEnd() == null && "FINISH".equals(request.getStatus())) {
            work.setActualEnd(Time.valueOf(LocalTime.now()));
        } else {
            throw WorkClosedException.EXCEPTION;
        }
    }

    @Transactional(readOnly = true)
    public TodoListResponse getAllTodo() {
        return TodoListResponse.builder()
                .todoList(
                        todoRepository.findAll().stream()
                                .collect(Collectors.groupingBy(it -> it.getConductAt().toLocalDate()))
                                .entrySet().stream().map(entry -> TodoGroupResponse.builder()
                                        .date(Formatters.DATE_FORMATTER.format(entry.getKey()))
                                        .todo(entry.getValue().stream().map(it -> TodoResponse.builder()
                                                        .id(it.getId())
                                                        .title(it.getName())
                                                        .description(it.getDescription())
                                                        .status(it.getStatus().toString())
                                                        .visibleToTeam(it.isVisibleToTeam())
                                                        .time(Formatters.HOUR_MINUTE_FORMATTER.format(it.getConductAt()))
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build()).collect(Collectors.toList())
                )
                .build();
    }

    @Transactional
    public void createTodo(EditTodoRequest request) {
        TodoEntity todo = TodoEntity.builder()
                .name(request.getTitle())
                .description(request.getDescription())
                .status(TodoStatus.NOT_YET)
                .conductAt(LocalDateTime.parse(request.getConductAt(), Formatters.DATE_TIME_FORMATTER))
                .visibleToTeam(request.getVisibleToTeam())
                .build();

        todoRepository.save(todo);
    }

    @Transactional
    public void editTodo(Long todoId, EditTodoRequest request) {
        TodoEntity todo = todoRepository.findById(todoId)
                .orElseThrow(() -> TodoNotFoundException.EXCEPTION);

        todo.update(request.getTitle(), request.getDescription(),
                LocalDateTime.parse(request.getConductAt(), Formatters.DATE_TIME_FORMATTER), request.getVisibleToTeam());
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        TodoEntity todo = todoRepository.findById(todoId)
                .orElseThrow(() -> TodoNotFoundException.EXCEPTION);

        todoRepository.delete(todo);
    }

    @Transactional
    public void changeTodoStatus(Long todoId, TodoStatusRequest request) {
        TodoEntity todo = todoRepository.findById(todoId)
                .orElseThrow(() -> TodoNotFoundException.EXCEPTION);

        todo.setStatus(request.getStatus());
    }
}

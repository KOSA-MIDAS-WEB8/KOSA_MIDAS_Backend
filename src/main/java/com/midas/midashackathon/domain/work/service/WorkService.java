package com.midas.midashackathon.domain.work.service;

import com.midas.midashackathon.domain.department.entity.DepartmentEntity;
import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.user.facade.UserFacade;
import com.midas.midashackathon.domain.work.entity.WorkEntity;
import com.midas.midashackathon.domain.work.exception.WorkClosedException;
import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import com.midas.midashackathon.domain.work.presentation.dto.request.WorkStatusRequest;
import com.midas.midashackathon.domain.work.presentation.dto.response.WorkStatusResponse;
import com.midas.midashackathon.domain.work.repository.WorkRepository;
import com.midas.midashackathon.global.utils.Formatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class WorkService {
    private final WorkRepository workRepository;
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
        LocalDate queryDate = LocalDate.parse(date, Formatters.DATE_FORMATTER);
        WorkEntity work = getWorkOrCreated(userFacade.queryCurrentUser(), queryDate);

        work.updatePlan(LocalTime.parse(request.getStart(), Formatters.HOUR_MINUTE_FORMATTER),
                LocalTime.parse(request.getEnd(), Formatters.HOUR_MINUTE_FORMATTER));
    }

    @Transactional
    public void updateActual(String date, WorkStatusRequest request) {
        LocalDate queryDate = LocalDate.parse(date, Formatters.DATE_FORMATTER);
        WorkEntity work = getWorkOrCreated(userFacade.queryCurrentUser(), queryDate);

        if(!LocalDate.now().isEqual(queryDate))
            throw WorkClosedException.EXCEPTION;

        if(work.getActualStart() == null) {
            work.setActualStart(Time.valueOf(LocalTime.now()));
        }else if(work.getActualStart() != null && work.getActualEnd() == null) {
            work.setActualEnd(Time.valueOf(LocalTime.now()));
        } else {
            throw WorkClosedException.EXCEPTION;
        }
    }
}

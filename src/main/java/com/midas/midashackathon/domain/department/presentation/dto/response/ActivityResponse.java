package com.midas.midashackathon.domain.department.presentation.dto.response;

import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import com.midas.midashackathon.domain.work.presentation.dto.response.TodoResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ActivityResponse {
    private String date;

    private List<TodoResponse> todoList;

    private WorkCommonDto plan;
}

package com.midas.midashackathon.domain.work.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TodoGroupResponse {
    private String date;
    private List<TodoResponse> todo;
}

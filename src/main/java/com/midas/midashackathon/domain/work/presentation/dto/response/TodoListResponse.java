package com.midas.midashackathon.domain.work.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TodoListResponse {
    private List<TodoGroupResponse> todoList;
}

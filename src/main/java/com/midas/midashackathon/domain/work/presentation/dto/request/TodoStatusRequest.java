package com.midas.midashackathon.domain.work.presentation.dto.request;

import com.midas.midashackathon.domain.work.type.TodoStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoStatusRequest {
    private TodoStatus status;
}

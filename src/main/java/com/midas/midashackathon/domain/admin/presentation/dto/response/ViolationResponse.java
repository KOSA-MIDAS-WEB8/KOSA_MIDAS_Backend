package com.midas.midashackathon.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViolationResponse {
    private Long userId;
    private String userName;
    private Double workHourAverage;
}

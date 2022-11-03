package com.midas.midashackathon.domain.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DepartmentResponse {
    private String code;
    private String name;
    private String coreTimeStart;
    private Integer coreTimeHours;
    private Integer workHour;
    private String defaultStartHour;
    private Integer memberCount;
}

package com.midas.midashackathon.domain.department.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentEditRequest {
    private String name;
    private String coreTimeStart;
    private String defaultStartHour;
    private Integer coreTimeHours;
    private Integer workHour;
}

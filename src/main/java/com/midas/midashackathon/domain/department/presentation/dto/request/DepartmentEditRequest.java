package com.midas.midashackathon.domain.department.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class DepartmentEditRequest {
    @NotNull
    private String name;

    @NotNull
    private String coreTimeStart;

    @NotNull
    private String defaultStartHour;

    @NotNull
    private Integer coreTimeHours;

    @NotNull
    private Integer workHour;
}

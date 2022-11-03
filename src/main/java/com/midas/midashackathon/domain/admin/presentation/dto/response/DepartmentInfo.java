package com.midas.midashackathon.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentInfo {
    private String code;
    private String name;
}

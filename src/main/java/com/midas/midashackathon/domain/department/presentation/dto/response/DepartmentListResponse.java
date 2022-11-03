package com.midas.midashackathon.domain.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DepartmentListResponse {
    private List<DepartmentResponse> departmentList;
}

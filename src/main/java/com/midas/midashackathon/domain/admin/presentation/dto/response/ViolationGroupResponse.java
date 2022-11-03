package com.midas.midashackathon.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ViolationGroupResponse {
    private String departmentId;
    private String departmentName;
    private List<ViolationResponse> violationHistory;
}

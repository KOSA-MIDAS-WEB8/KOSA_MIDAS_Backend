package com.midas.midashackathon.domain.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberResponse {
    private Long userId;
    private String name;
    private List<ActivityResponse> activity;
}

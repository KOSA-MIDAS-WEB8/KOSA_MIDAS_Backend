package com.midas.midashackathon.domain.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private Long userId;
    private String name;
}

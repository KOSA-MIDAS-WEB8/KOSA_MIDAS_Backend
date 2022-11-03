package com.midas.midashackathon.domain.department.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberListResponse {
    private List<MemberResponse> members;
}

package com.midas.midashackathon.domain.work.presentation.dto.response;

import com.midas.midashackathon.domain.work.presentation.dto.WorkCommonDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkStatusResponse {
    private WorkCommonDto plan;
    private WorkCommonDto actual;
}

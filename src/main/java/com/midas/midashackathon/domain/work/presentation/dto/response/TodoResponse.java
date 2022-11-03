package com.midas.midashackathon.domain.work.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private String time;
    private String status;
    private Boolean visibleToTeam;
}

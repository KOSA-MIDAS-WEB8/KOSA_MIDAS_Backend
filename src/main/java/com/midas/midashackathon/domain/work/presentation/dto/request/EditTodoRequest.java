package com.midas.midashackathon.domain.work.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditTodoRequest {
    private String title;
    private String description;
    private String conductAt;
    private Boolean visibleToTeam;
}

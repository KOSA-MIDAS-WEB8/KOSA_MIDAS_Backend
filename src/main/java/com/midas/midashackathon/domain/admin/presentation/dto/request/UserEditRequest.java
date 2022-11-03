package com.midas.midashackathon.domain.admin.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEditRequest {
    private String name;
    private String department;
    private Boolean isAdmin;
}

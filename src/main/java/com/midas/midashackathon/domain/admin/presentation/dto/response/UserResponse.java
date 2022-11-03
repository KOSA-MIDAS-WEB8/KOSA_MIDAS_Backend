package com.midas.midashackathon.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String accountId;
    private String phoneNumber;
    private Boolean isAdmin;
    private DepartmentInfo departmentInfo;
}

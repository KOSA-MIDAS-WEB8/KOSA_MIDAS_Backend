package com.midas.midashackathon.domain.user.presentation.dto.response;

import com.midas.midashackathon.domain.user.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SignInResponse {
    private String accessToken;
    private Role role;
}

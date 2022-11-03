package com.midas.midashackathon.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignInRequest {
    @NotNull
    @Size(min = 4, max = 16, message = "아이디는 4~16자리여야 합니다")
    private String id;

    @NotNull
    @Size(min = 8, max = 32, message = "비밀번호는 8자리 이상 32자리 이하여야 합니다")
    private String password;
}

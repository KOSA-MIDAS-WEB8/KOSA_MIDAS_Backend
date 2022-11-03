package com.midas.midashackathon.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotNull
    @Size(min = 4, max = 16, message = "아이디는 4~16자리여야 합니다")
    private String id;

    @NotNull
    @Size(min = 8, max = 32, message = "비밀번호는 8자리 이상 32자리 이하여야 합니다")
    private String password;

    @NotNull
    @Size(min = 2, max = 4, message = "이름은 2자리 이상 4자리 이하여야 합니다")
    private String name;

    @NotNull
    @Pattern(regexp = "\\d{2,3}-\\d{4,4}-\\d{4,4}", message = "010-1234-5678 형식이여야 합니다")
    private String phoneNumber;

    @NotNull
    @Size(min = 6, max = 6, message = "부서 코드는 6자리여야 합니다")
    private String department;

    @NotNull(message = "관리자 여부는 null이여서는 안됩니다")
    private Boolean isAdmin;
}

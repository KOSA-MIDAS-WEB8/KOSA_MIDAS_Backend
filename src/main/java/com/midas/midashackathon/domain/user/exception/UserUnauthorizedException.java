package com.midas.midashackathon.domain.user.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserUnauthorizedException extends BusinessException {
    private UserUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자");
    }

    public static final BusinessException EXCEPTION = new UserUnauthorizedException();
}

package com.midas.midashackathon.domain.user.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
    private UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다");
    }

    public static final BusinessException EXCEPTION = new UserNotFoundException();
}

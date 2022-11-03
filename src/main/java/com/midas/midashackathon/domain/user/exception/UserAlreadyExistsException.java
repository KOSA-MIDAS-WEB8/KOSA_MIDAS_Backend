package com.midas.midashackathon.domain.user.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BusinessException {
    private UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "이미 존재하는 사용자 아이디");
    }

    public static final BusinessException EXCEPTION = new UserAlreadyExistsException();
}

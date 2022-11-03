package com.midas.midashackathon.domain.work.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class TodoNotFoundException extends BusinessException {
    private TodoNotFoundException() {
        super(HttpStatus.NOT_FOUND, "찾을 수 없는 TODO입니다");
    }

    public static BusinessException EXCEPTION = new TodoNotFoundException();
}

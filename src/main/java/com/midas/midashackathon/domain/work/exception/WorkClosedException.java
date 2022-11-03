package com.midas.midashackathon.domain.work.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class WorkClosedException extends BusinessException {
    private WorkClosedException() {
        super(HttpStatus.FORBIDDEN, "접근할 수 없는 날입니다");
    }

    public static BusinessException EXCEPTION = new WorkClosedException();
}

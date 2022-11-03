package com.midas.midashackathon.domain.work.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PlanNotQualifiedException extends BusinessException {
    private PlanNotQualifiedException() {
        super(HttpStatus.BAD_REQUEST, "일정은 반드시 코어타임을 포함해야 합니다");
    }

    public static BusinessException EXCEPTION = new PlanNotQualifiedException();
}

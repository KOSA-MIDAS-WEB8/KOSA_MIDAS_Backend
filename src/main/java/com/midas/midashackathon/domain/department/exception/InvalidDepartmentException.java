package com.midas.midashackathon.domain.department.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidDepartmentException extends BusinessException {
    private InvalidDepartmentException() {
        super(HttpStatus.NOT_FOUND, "찾을 수 없는 부서");
    }

    public static final BusinessException EXCEPTION = new InvalidDepartmentException();
}

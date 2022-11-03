package com.midas.midashackathon.domain.department.exception;

import com.midas.midashackathon.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DepartmentNotFoundException extends BusinessException {
    private DepartmentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당하는 부서를 찾을 수 없습니다");
    }

    public static BusinessException EXCEPTION = new DepartmentNotFoundException();
}

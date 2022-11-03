package com.midas.midashackathon.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class BusinessException extends RuntimeException {
    private HttpStatus status;
    private String message;
}

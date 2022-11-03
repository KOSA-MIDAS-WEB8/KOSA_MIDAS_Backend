package com.midas.midashackathon.global.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int code;
    private String message;
}

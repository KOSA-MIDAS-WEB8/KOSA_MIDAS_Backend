package com.midas.midashackathon.global.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> uncaughtException(RuntimeException exception) {
        exception.printStackTrace();

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("알 수 없는 오류가 발생하였습니다.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessLogic(BusinessException exception) {
        log.error(exception);

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(exception.getStatus().value())
                .message(exception.getMessage())
                .build(), exception.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindError(BindException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getFieldErrors().get(0).getDefaultMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> badRequest(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("잘못된 요청 형식")
                .build(), HttpStatus.BAD_REQUEST);
    }

}

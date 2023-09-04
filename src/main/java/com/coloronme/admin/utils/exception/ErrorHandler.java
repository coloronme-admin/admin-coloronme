package com.coloronme.admin.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    public ErrorResponse apiException(ApiException e){
        return new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e){
        return new ErrorResponse(400, e.getFieldError().getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ErrorResponse duplicateException(DuplicateException e){
        return new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidByEmailException.class)
    public ErrorResponse invalidByEmailException(InvalidByEmailException e){
        return new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
}

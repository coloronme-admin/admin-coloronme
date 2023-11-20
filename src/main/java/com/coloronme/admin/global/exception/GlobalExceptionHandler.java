package com.coloronme.admin.global.exception;


import com.coloronme.admin.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestException.class)
    public ErrorResponseDto handleApiRequestException(RequestException e) {
        return new ErrorResponseDto(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponseDto(400, e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ErrorResponseDto dateTimeParseException(DateTimeParseException e) {
        return new ErrorResponseDto(400, "유효하지 않은 날짜입니다.");
    }
}

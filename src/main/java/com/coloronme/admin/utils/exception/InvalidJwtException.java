package com.coloronme.admin.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidJwtException extends RuntimeException{
    private final ErrorCode errorCode;
}

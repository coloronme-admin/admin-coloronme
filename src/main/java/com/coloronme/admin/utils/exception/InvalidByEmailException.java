package com.coloronme.admin.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidByEmailException extends RuntimeException{
    private final ErrorCode errorCode;
}

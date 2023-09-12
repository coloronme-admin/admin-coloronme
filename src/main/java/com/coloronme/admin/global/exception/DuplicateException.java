package com.coloronme.admin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateException extends RuntimeException{
    private final ErrorCode errorCode;
}

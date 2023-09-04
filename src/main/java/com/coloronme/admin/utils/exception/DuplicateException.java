package com.coloronme.admin.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateException extends RuntimeException{
    private final ErrorCode errorCode;
}

package com.coloronme.admin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /*CONSULTANT*/
    EMAIL_DUPLICATION_409(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
    CONSULTANT_NOT_FOUND_404(HttpStatus.NOT_FOUND, "진단자를 찾을 수 없습니다."),
    PASSWORD_NOT_FOUND_404(HttpStatus.NOT_FOUND, "비밀번호를 찾을 수 없습니다."),
    LOGIN_NOT_FOUND_404(HttpStatus.NOT_FOUND, "아이디 또는 비밀번호를 잘못 입력했습니다."),

    /*USER*/
    USER_NOT_FOUND_404(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    /*PERSONAL COLOR*/
    PERSONAL_COLOR_NOT_FOUND_404(HttpStatus.NOT_FOUND, "퍼스널 컬러를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

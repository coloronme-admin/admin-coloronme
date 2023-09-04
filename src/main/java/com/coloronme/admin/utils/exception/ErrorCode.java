package com.coloronme.admin.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_BY_EMAIL(901, "중복된 이메일 주소입니다."), /*Todo 에러코드 확인 필요*/
    INVALID_BY_EMAIL(902, "유효하지 않은 이메일 주소입니다."),
    NOT_FOUND_ACCOUNT(903, "아이디 또는 비밀번호를 확인해 주세요.");

    private final int code;
    private final String message;
}

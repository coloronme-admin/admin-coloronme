package com.coloronme.admin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /*CONSULTANT*/
    USER_DUPLICATION_409(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
    CONSULTANT_NOT_FOUND_404(HttpStatus.NOT_FOUND, "진단자를 찾을 수 없습니다."),
    PASSWORD_NOT_FOUND_404(HttpStatus.NOT_FOUND, "비밀번호를 찾을 수 없습니다."),
    LOGIN_NOT_FOUND_404(HttpStatus.NOT_FOUND, "아이디 또는 비밀번호를 잘못 입력했습니다."),

    /*USER*/
    USER_NOT_FOUND_404(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    UUID_NOT_FOUND_404(HttpStatus.NOT_FOUND, "UUID를 찾을 수 없습니다."),
    QR_EXPIRED_AT_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "만료된 QR 코드입니다."),

    /*PERSONAL COLOR*/
    PERSONAL_COLOR_NOT_FOUND_404(HttpStatus.NOT_FOUND, "퍼스널 컬러를 찾을 수 없습니다."),
    PERSONAL_COLOR_TYPE_NOT_FOUND_404(HttpStatus.NOT_FOUND, "퍼스널 컬러 타입을 찾을 수 없습니다."),
    PERSONAL_COLOR_GROUP_NOT_FOUND_404(HttpStatus.NOT_FOUND, "퍼스널 컬러 그룹을 찾을 수 없습니다."),


    /*CONSULT*/
    CONSULT_NOT_FOUND_404(HttpStatus.NOT_FOUND, "진단 내역을 찾을 수 없습니다."),

    /*MY PAGE*/
    MY_PAGE_NOT_FOUND_404(HttpStatus.NOT_FOUND, "마이페이지 내용을 찾을 수 없습니다."),
    OLD_PASSWORD_NOT_FOUND_404(HttpStatus.NOT_FOUND, "기존 패스워드를 잘못 입력했습니다."),
    PASSWORD_CONFIRM_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    EMAIL_DUPLICATION_409(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    /*MainPage*/
    INVALID_LOCALDATE_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "from은 to보다 이후일 수 없습니다."),
    INVALID_ENUM_TYPE_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "유효하지 않은 타입입니다."),

    /*Color*/
    COLOR_NOT_FOUND_404(HttpStatus.NOT_FOUND, "컬러를 찾을 수 없습니다."),

    /*JWT*/
    JWT_BAD_TOKEN_500(HttpStatus.INTERNAL_SERVER_ERROR, "토큰이 유효하지 않습니다.");



    private final HttpStatus httpStatus;
    private final String message;
}

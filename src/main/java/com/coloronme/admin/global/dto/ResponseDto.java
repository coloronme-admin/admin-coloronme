package com.coloronme.admin.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> success(boolean success, T data) {
        return new ResponseDto<>(false, data, null);
    }

    public static <T> ResponseDto<T> fail(HttpStatus httpStatus, String message) {
        return new ResponseDto<>(false, null, new Error(httpStatus, message));
    }
}

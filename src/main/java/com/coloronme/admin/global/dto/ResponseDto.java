package com.coloronme.admin.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private String status;
    private T data;

    public static <T> ResponseDto<T> status(T data) {
        return new ResponseDto<>("success", data);
    }

    public static <T> ResponseDto<T> status() {
        return new ResponseDto<>("success");
    }

    public ResponseDto(String status) {
        this.status = status;
    }
}

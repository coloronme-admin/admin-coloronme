package com.coloronme.admin.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private String status;
    private T data;

    public static <T> ResponseDto<T> status(T data) {
        return new ResponseDto<>("success", data);
    }
}

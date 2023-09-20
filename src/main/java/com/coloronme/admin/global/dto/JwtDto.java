package com.coloronme.admin.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}

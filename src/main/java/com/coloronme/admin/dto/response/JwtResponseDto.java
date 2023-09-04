package com.coloronme.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponseDto {
    private String result;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;
}

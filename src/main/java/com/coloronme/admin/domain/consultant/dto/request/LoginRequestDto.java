package com.coloronme.admin.domain.consultant.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @Schema(description = "이메일", example = "email@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "password")
    private String password;
}

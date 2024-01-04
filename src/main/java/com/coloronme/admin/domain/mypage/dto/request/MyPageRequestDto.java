package com.coloronme.admin.domain.mypage.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageRequestDto {


    @Schema(description = "이름", example = "변경할 이름")
    String name;
    @Schema(description = "회사", example = "변경할 회사명")
    String company;

    @Schema(description = "이메일", example = "변경@naver.com")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    String email;
}

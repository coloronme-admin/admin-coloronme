package com.coloronme.admin.domain.consultant.dto.request;

import com.coloronme.admin.global.annotation.ValueOfEnum;
import com.coloronme.product.personalColor.dto.ColorGroup;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantRequestDto {

    @Schema(description = "이메일", example = "email@naver.com")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다")
    private String password;

    @NotNull(message = "색상군을 입력해 주세요.")
    @ValueOfEnum(enumClass = ColorGroup.class)
    private ColorGroup colorGroup;

    @Hidden
    public void setEncodedPwd(String encodedPwd) {
        this.password = encodedPwd;
    }
}


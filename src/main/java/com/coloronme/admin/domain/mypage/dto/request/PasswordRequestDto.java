package com.coloronme.admin.domain.mypage.dto.request;


import com.coloronme.admin.domain.consultant.entity.Consultant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PasswordRequestDto {

    @Schema(description = "현재 비밀번호", example = "password1241231!")
    @NotBlank(message = "기존 비밀번호와 일치하지 않습니다.")
    private String oldPassword;


    @Schema(description = "새로운 비밀번호", example = "newPassword1241231!")
    @NotBlank(message = "비밀번호는 필수 입력 값 잆니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문,숫자,특수문자 포함 8자 ~ 20자로 입력해주세요.")
    private String newPassword;

    @Schema(description = "새로운 비밀번호 확인", example = "newPassword1241231!")
    @NotBlank(message = "비밀번호가 일치하지 않습니다.")
    private String newPasswordConfirm;

    public PasswordRequestDto(String oldPassword, String newPassword, String newPasswordConfirm) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public void setEncodedPwd(String encodedPwd) {
        this.newPassword = encodedPwd;
    }

}

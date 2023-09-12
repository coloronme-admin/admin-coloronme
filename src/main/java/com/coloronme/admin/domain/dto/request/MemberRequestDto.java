package com.coloronme.admin.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    @NotBlank(message="email을 입력해 주세요.")
    private String email;
    @NotBlank(message="비밀번호를 입력해 주세요.")
    private String password;
}

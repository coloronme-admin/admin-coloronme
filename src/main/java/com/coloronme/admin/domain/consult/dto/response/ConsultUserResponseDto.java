package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.admin.domain.member.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ConsultUserResponseDto {
    private String nickname;
    private String email;
    private LocalDateTime personalDate;
    private Long personalColorId;
    private String age;
    private Gender gender;
    private String consultContent;
}
  
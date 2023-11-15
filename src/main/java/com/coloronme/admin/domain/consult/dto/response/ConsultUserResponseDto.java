package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.member.enums.Gender;
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
    private int personalColorId;
    private int age;
    private Gender gender;
    private String consultContent;
    private String consultDrawing;
}
  
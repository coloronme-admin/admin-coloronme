package com.coloronme.admin.domain.consult.dto.response;

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
    private String gender;
    private String consultContent;
}

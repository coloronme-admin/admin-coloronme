package com.coloronme.admin.domain.consult.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultRequestDto {
    @NotBlank(message = "퍼스널 컬러를 선택해 주세요.")
    private Long personalColorId;
    private String consultContent;
    private LocalDateTime consultDate;
}

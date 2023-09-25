package com.coloronme.admin.domain.consult.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "퍼스널 컬러를 선택해 주세요.")
    @JsonProperty(value = "personal_color_id")
    private Long personalColorId;
    @JsonProperty(value = "consult_content")
    private String consultContent;
    @JsonProperty(value = "consult_date")
    private LocalDateTime consultDate;
}

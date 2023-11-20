package com.coloronme.admin.domain.consult.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
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
    private int personalColorId;
    private String consultedContent;
    private String consultedDrawing;
    @Future(message = "상담 날짜가 현재보다 이전일 수 없습니다.")
    private LocalDateTime consultedDate;
}

package com.coloronme.admin.domain.consult.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultResponseDto {
    private String status;
    private String message;
}

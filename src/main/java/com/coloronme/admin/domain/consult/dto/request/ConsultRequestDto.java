package com.coloronme.admin.domain.consult.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultRequestDto {
    @NotNull(message = "퍼스널 컬러를 선택해 주세요.")
    private int personalColorId;
    private String consultedContent;
    private String consultedDrawing;
    private LocalDateTime consultedDate;
    private String uuid;
    private String consultedFile;
}

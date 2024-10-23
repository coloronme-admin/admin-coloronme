package com.coloronme.admin.domain.consult.dto.request;

import com.coloronme.product.color.dto.ColorRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultRequestDto {
    @NotNull(message = "퍼스널 컬러 타입을 선택해 주세요.")
    private int personalColorTypeId;
    private String consultedContent;
    private String consultedDrawing;
    @NotNull(message = "진단 날짜를 확인해 주세요.")
    private LocalDateTime consultedDate;
    private String uuid;
    @NotNull(message = "색상을 선택해 주세요.")
    private List<ColorRequestDto> colors;
    private String consultedFile;
}

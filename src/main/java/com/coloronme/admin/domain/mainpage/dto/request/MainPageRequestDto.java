package com.coloronme.admin.domain.mainpage.dto.request;

import com.coloronme.admin.domain.mainpage.dto.DataType;
import com.coloronme.admin.global.enumHandler.ValueOfEnum;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MainPageRequestDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "시작일을 입력해 주세요.")
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "종료일을 입력해 주세요.")
    @PastOrPresent(message = "to가 현재 날짜를 초과했습니다.")
    private LocalDate to;
    @NotNull(message = "정렬 데이터 수를 입력해 주세요.")
    @Min(value = 0, message = "올바른 데이터 수를 입력해 주세요.")
    private int top;
    @NotNull(message = "타입을 입력해 주세요.")
    @ValueOfEnum(enumClass=DataType.class)
    private String type;
}

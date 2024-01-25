package com.coloronme.admin.domain.mainpage.dto.request;

import com.coloronme.admin.domain.mainpage.dto.DataType;
import com.coloronme.admin.global.annotation.ValueOfDate;
import com.coloronme.admin.global.annotation.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MainPageRequestDto {
    @ValueOfDate
    @NotNull(message = "시작일을 입력해 주세요.")
    private String from;
    @ValueOfDate(isTo = true)
    @NotNull(message = "종료일을 입력해 주세요.")
    private String to;
    @NotNull(message = "정렬 데이터 수를 입력해 주세요.")
    @Min(value = 0, message = "올바른 데이터 수를 입력해 주세요.")
    private int top;
    @NotNull(message = "타입을 입력해 주세요.")
    @ValueOfEnum(enumClass=DataType.class)
    private String type;
}

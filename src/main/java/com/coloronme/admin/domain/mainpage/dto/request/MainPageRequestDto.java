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
@Getter
public class MainPageRequestDto {
    @ValueOfDate
    private String from;
    @ValueOfDate(isTo = true)
    private String to;
    @Min(value = 0, message = "올바른 데이터 수를 입력해 주세요.")
    private int top;
    @NotNull(message = "타입을 입력해 주세요.")
    @ValueOfEnum(enumClass=DataType.class)
    private String type;

    public MainPageRequestDto() {
        this.from = LocalDate.now().minusMonths(1).toString();
        this.to = LocalDate.now().toString();
        this.top = 5;
    }
}

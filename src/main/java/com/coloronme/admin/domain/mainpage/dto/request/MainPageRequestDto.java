package com.coloronme.admin.domain.mainpage.dto.request;

import com.coloronme.admin.domain.mainpage.dto.DataType;
import com.coloronme.admin.domain.mainpage.dto.PrincipalType;
import com.coloronme.admin.global.annotation.ValueOfDate;
import com.coloronme.admin.global.annotation.ValueOfEnum;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@AllArgsConstructor
@Getter
public class MainPageRequestDto {
    @ValueOfDate(isFrom = true)
    private String from;
    @ValueOfDate(isTo = true)
    private String to;
    @Min(value = 0, message = "올바른 데이터 수를 입력해 주세요.")
    private int top;
    @NotNull(message = "타입을 입력해 주세요.")
    @ValueOfEnum(enumClass=DataType.class)
    private String type;
    @ValueOfEnum(enumClass = PrincipalType.class)
    private String principal;

    public MainPageRequestDto() {
        this.from = LocalDate.now().minusMonths(1).toString();
        this.to = LocalDate.now().toString();
        this.top = 5;
    }

    public void invalidCheckFromTo() {
        LocalDate fromDate = LocalDate.from(LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate toDate = LocalDate.from(LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if(fromDate.isAfter(toDate)) {
            throw new RequestException(ErrorCode.INVALID_LOCALDATE_BAD_REQUEST_400);
        }
    }
}
package com.coloronme.admin.domain.consult.dto.request;

import com.coloronme.admin.global.annotation.ValueOfEnum;
import com.coloronme.product.personalColor.dto.PersonalColorGroupEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonalColorTypeRequestDto {
    @NotNull(message = "퍼스널 컬러 그룹은 필수 입력값입니다.")
    @ValueOfEnum(enumClass = PersonalColorGroupEnum.class, message = "유효하지 않는 퍼스널 컬러 그룹 값입니다.")
    private String personalColorGroup;
    @NotNull(message = "퍼스널 컬러 타입 이름은 필수 입력값입니다.")
    private String personalColorTypeName;
    @NotNull(message = "디폴트 컬러를 선택해 주세요.")
    private List<Integer> colors;

    public PersonalColorGroupEnum getPersonalColorGroupEnum() {
        return PersonalColorGroupEnum.valueOf(this.personalColorGroup.toUpperCase());
    }
}

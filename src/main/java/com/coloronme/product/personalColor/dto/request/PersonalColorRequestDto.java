package com.coloronme.product.personalColor.dto.request;

import com.coloronme.admin.global.annotation.ValueOfEnum;
import com.coloronme.product.personalColor.dto.ColorGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PersonalColorRequestDto {
    @NotNull(message = "type은 null이 될 수 없습니다.")
    @ValueOfEnum(enumClass = ColorGroup.class)
    private ColorGroup type;
    private String group;


}

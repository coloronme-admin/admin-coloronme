package com.coloronme.product.personalColor.dto.request;

import com.coloronme.admin.global.annotation.ValueOfEnum;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.PersonalColor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalColorRequestDto {
    @NotNull(message = "type은 null일 수 없습니다.")
    @ValueOfEnum(enumClass = ColorGroup.class, message = "유효하지 않은 type 값입니다.")
    private String type;
    @ValueOfEnum(enumClass = PersonalColor.class, message = "유효하지 않은 group 값입니다.")
    private String group;

    public ColorGroup getColorGroupEnum() {
        return ColorGroup.valueOf(this.type.toUpperCase());
    }
}

package com.coloronme.product.personalColor.dto.request;

import com.coloronme.admin.global.annotation.ValueOfEnum;
import com.coloronme.product.personalColor.dto.ColorGroup;
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
    //@ValueOfEnum(enumClass = ColorGroup.class, message = "유효하지 않은 type 값입니다.")
    private ColorGroup type;
    private String group;

    public void setType(String type) {
        this.type = ColorGroup.valueOf(type.toUpperCase());
    }
}

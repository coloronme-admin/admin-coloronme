package com.coloronme.product.personalColor.dto.response;

import com.coloronme.product.personalColor.dto.ColorGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorGroupResponseDto {
    private PersonalColorResponseDto pccs;
    private PersonalColorResponseDto ks;

    public ColorGroupResponseDto(ColorGroup colorGroup) {
        if (colorGroup == ColorGroup.ALL) {
            this.pccs = new PersonalColorResponseDto(ColorGroup.PCCS);
            this.ks = new PersonalColorResponseDto(ColorGroup.KS);
        } else if (colorGroup == ColorGroup.KS) {
            this.ks = new PersonalColorResponseDto(colorGroup);
        } else if (colorGroup == ColorGroup.PCCS) {
            this.pccs = new PersonalColorResponseDto(colorGroup);
        }
    }
}

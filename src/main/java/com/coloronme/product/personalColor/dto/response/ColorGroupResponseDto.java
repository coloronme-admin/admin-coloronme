package com.coloronme.product.personalColor.dto.response;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorGroupResponseDto {
    private PersonalColorResponseDto pccs;
    private PersonalColorResponseDto ks;
}

package com.coloronme.product.personalColor.dto.response;

import com.coloronme.product.color.dto.ColorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PersonalColorTypeDto {
    private Integer personalColorTypeId;
    private String personalColorTypeName;
    private List<ColorResponseDto> colors;
}

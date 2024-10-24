package com.coloronme.admin.domain.consult.dto.request;

import com.coloronme.product.color.dto.ColorRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonalColorTypeRequestDto {
    private String personalColorGroup;
    private String personalColorTypeName;
    private List<ColorRequestDto> colors;
}

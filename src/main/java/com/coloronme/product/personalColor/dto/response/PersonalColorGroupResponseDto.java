package com.coloronme.product.personalColor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PersonalColorGroupResponseDto {
    private String personalColorGroupName;
    private List<PersonalColorTypeDto> personalColorTypes;
}

package com.coloronme.product.personalColor.dto;

import com.coloronme.product.personalColor.entity.PersonalColorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PersonalColorGroupResponseDto {
    private Integer personalColorGroupId;
    private String personalColorGroupName;
    private List<PersonalColorTypeDto> personalColorTypes;
}

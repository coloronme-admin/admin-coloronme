package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private List<ColorResponseDto> colors;
}

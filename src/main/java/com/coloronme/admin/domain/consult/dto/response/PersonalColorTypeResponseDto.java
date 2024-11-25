package com.coloronme.admin.domain.consult.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PersonalColorTypeResponseDto {
    private String personalColorGroupName;
    private PersonalColorTypeDto personalColorType;
}

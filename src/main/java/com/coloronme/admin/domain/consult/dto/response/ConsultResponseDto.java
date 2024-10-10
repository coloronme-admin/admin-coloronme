package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.personalColor.dto.PersonalColorGroupResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ConsultResponseDto {
    private List<PersonalColorGroupResponseDto> personalColorGroups;
    private ConsultUserResponseDto consultUserResponseDto;
}

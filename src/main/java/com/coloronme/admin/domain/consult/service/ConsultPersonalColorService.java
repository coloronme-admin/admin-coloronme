package com.coloronme.admin.domain.consult.service;

import com.coloronme.product.personalColor.dto.response.PersonalColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorTypeDto;
import com.coloronme.product.personalColor.entity.PersonalColorGroup;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultPersonalColorService {
    /*if(consultantId != null) {
        List<PersonalColorGroup> personalColorGroups = personalColorGroupRepository.findAllByConsultantIdWithTypes(consultantId);

        List<PersonalColorGroupResponseDto> personalColorGroupList = personalColorGroups.stream().map(personalColorGroup -> {
            *//*각 PersonalColorType을 personalColorTypeList 변환하여 리스트에 추가*//*
            List<PersonalColorTypeDto> personalColorTypeList = personalColorGroup.getPersonalColorTypes().stream()
                    .filter(type -> type.getConsultantId().equals(consultantId))
                    .map(type -> new PersonalColorTypeDto(type.getId(), type.getPersonalColorTypeName()))
                    .collect(Collectors.toList());

            return PersonalColorGroupResponseDto.builder()
                    .personalColorGroupId(personalColorGroup.getId())
                    .personalColorGroupName(personalColorGroup.getPersonalColorGroupName())
                    .personalColorTypes(personalColorTypeList)
                    .build();

        }).collect(Collectors.toList());

        consultUserResponseDto.setPersonalColorGroups(personalColorGroupList);

    }*/
}

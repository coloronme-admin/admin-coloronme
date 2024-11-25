package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultPersonalColorResponseDto;
import com.coloronme.admin.domain.consult.entity.ColorPersonalColorType;
import com.coloronme.admin.domain.consult.repository.ColorPersonalColorTypeRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.color.entity.Color;
import com.coloronme.product.color.repository.ColorRepository;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeResponseDto;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeDto;
import com.coloronme.product.personalColor.dto.PersonalColorGroupEnum;
import com.coloronme.product.personalColor.entity.PersonalColorGroup;
import com.coloronme.product.personalColor.entity.PersonalColorType;
import com.coloronme.product.personalColor.repository.PersonalColorGroupRepository;
import com.coloronme.product.personalColor.repository.PersonalColorTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConsultPersonalColorService {

    private final PersonalColorGroupRepository personalColorGroupRepository;
    private final ColorRepository colorRepository;
    private final ColorPersonalColorTypeRepository colorPersonalColorTypeRepository;
    private final PersonalColorTypeRepository personalColorTypeRepository;

    public ConsultPersonalColorResponseDto getPersonalColorType(int consultantId) {

        ConsultPersonalColorResponseDto consultPersonalColorResponseDto = new ConsultPersonalColorResponseDto();

        consultPersonalColorResponseDto.setSpring(getPersonalColorDtoList(consultantId, PersonalColorGroupEnum.SPRING));
        consultPersonalColorResponseDto.setSummer(getPersonalColorDtoList(consultantId, PersonalColorGroupEnum.SUMMER));
        consultPersonalColorResponseDto.setFall(getPersonalColorDtoList(consultantId, PersonalColorGroupEnum.FALL));
        consultPersonalColorResponseDto.setWinter(getPersonalColorDtoList(consultantId, PersonalColorGroupEnum.WINTER));

        return consultPersonalColorResponseDto;
    }

    @Transactional
    public PersonalColorTypeResponseDto registerPersonalColorType(Integer consultantId, PersonalColorTypeRequestDto personalColorTypeRequestDto) {
        /*퍼스널 컬러 그룹 확인*/
        PersonalColorGroup personalColorGroup = personalColorGroupRepository
                .findByPersonalColorGroupName(personalColorTypeRequestDto.getPersonalColorGroup());
        if(personalColorGroup == null) {
            throw new RequestException(ErrorCode.PERSONAL_COLOR_GROUP_NOT_FOUND_404);
        }

        /*PersonalColorType 추가*/
        PersonalColorType personalColorType = PersonalColorType.builder()
                .consultantId(consultantId)
                .personalColorTypeName(personalColorTypeRequestDto.getPersonalColorTypeName())
                .personalColorGroup(personalColorGroup)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<ColorPersonalColorType> colorPersonalColorTypeList = new ArrayList<>();

        for(int i=0 ; i<personalColorTypeRequestDto.getColors().size() ; i++) {
            ColorPersonalColorType colorPersonalColorType = new ColorPersonalColorType();
            colorPersonalColorType.setPersonalColorType(personalColorType);

            Color color = colorRepository.findById(personalColorTypeRequestDto.getColors().get(i).getColorId())
                    .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));

            colorPersonalColorType.setColor(color);

            colorPersonalColorTypeRepository.save(colorPersonalColorType);

            colorPersonalColorTypeList.add(colorPersonalColorType);
        }

        personalColorType.setColorPersonalColorTypeList(colorPersonalColorTypeList);

        personalColorTypeRepository.save(personalColorType);

        return getPersonalColorTypeResponseDto(personalColorType, personalColorGroup);
    }

    private PersonalColorTypeResponseDto getPersonalColorTypeResponseDto(PersonalColorType personalColorType, PersonalColorGroup personalColorGroup) {
        return new PersonalColorTypeResponseDto(
                personalColorGroup.getPersonalColorGroupName(),
                toPersonalColorTypeDto(personalColorType)
        );
    }

    private List<PersonalColorTypeDto> getPersonalColorDtoList(int consultantId, PersonalColorGroupEnum groupName) {
        List<PersonalColorType> personalColorTypes = personalColorTypeRepository.findPersonalColorTypeByGroup(consultantId, groupName.toLowerCase());
        return personalColorTypes.stream()
                .map(this::toPersonalColorTypeDto)
                .collect(Collectors.toList());
    }

    private PersonalColorTypeDto toPersonalColorTypeDto(PersonalColorType personalColorType) {
        List<ColorResponseDto> colorResponseList = personalColorType.getColorPersonalColorTypeList().stream()
                .map(mapping -> {
                    Color color = mapping.getColor();
                    return new ColorResponseDto(color.getId(), color.getName(), color.getR(), color.getG(), color.getB());
                })
                .collect(Collectors.toList());

        return new PersonalColorTypeDto(
                personalColorType.getId(),
                personalColorType.getPersonalColorTypeName(),
                colorResponseList
        );
    }
}

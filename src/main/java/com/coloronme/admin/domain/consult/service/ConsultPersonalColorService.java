package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultPersonalColorResponseDto;
import com.coloronme.admin.domain.consult.entity.ColorPersonalColorType;
import com.coloronme.admin.domain.consult.repository.ColorPersonalColorTypeRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.product.color.dto.ColorRequestDto;
import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.color.entity.Color;
import com.coloronme.product.color.repository.ColorRepository;
import com.coloronme.product.personalColor.dto.response.PersonalColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorTypeDto;
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

@RequiredArgsConstructor
@Service
public class ConsultPersonalColorService {

    private final PersonalColorGroupRepository personalColorGroupRepository;
    private final ColorRepository colorRepository;
    private final ColorPersonalColorTypeRepository colorPersonalColorTypeRepository;
    private final PersonalColorTypeRepository personalColorTypeRepository;

    public ConsultPersonalColorResponseDto getPersonalColorType(int consultantId) {

        /**/

        List<PersonalColorGroup> personalColorGroups = personalColorGroupRepository.findAllByConsultantIdWithTypes(consultantId);



//        List<PersonalColorGroupResponseDto> personalColorGroupList = personalColorGroups.stream().map(personalColorGroup -> {
//            /*각 PersonalColorType을 personalColorTypeList 변환하여 리스트에 추가*/
//            List<PersonalColorTypeDto> personalColorTypeList = personalColorGroup.getPersonalColorTypes().stream()
//                    .filter(type -> type.getConsultantId().equals(consultantId))
//                    .map(type -> new PersonalColorTypeDto(type.getId(), type.getPersonalColorTypeName()))
//                    .collect(Collectors.toList());
//
//            return PersonalColorGroupResponseDto.builder()
//                    .personalColorGroupId(personalColorGroup.getId())
//                    .personalColorGroupName(personalColorGroup.getPersonalColorGroupName())
//                    .personalColorTypes(personalColorTypeList)
//                    .build();
//
//        }).collect(Collectors.toList());
//
//        consultUserResponseDto.setPersonalColorGroups(personalColorGroupList);
        return null;
    }

    @Transactional
    public PersonalColorGroupResponseDto registerPersonalColorType(Integer consultantId, PersonalColorTypeRequestDto personalColorTypeRequestDto) {
        /*유효성 검사*/


        System.out.println("personalColorTypeRequestDto.getPersonalColorTypeName()" + personalColorTypeRequestDto.getPersonalColorTypeName());

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

        /*DB 추가 완료*/
        personalColorTypeRepository.save(personalColorType);

        List<ColorResponseDto> colorList = new ArrayList<>();


        /*해당 PersonalColorType에 속한 Color 추가*/
        for(ColorRequestDto colorRequestDto : personalColorTypeRequestDto.getColors()) {

            Color color = colorRepository.findById(colorRequestDto.getColorId())
                    .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));

            ColorPersonalColorType colorPersonalColorType = new ColorPersonalColorType();
            colorPersonalColorType.setColor(color);
            colorPersonalColorType.setPersonalColorType(personalColorType);

            colorPersonalColorTypeRepository.save(colorPersonalColorType);

            ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                    .colorId(color.getId())
                    .name(color.getName())
                    .r(color.getR())
                    .g(color.getG())
                    .b(color.getB())
                    .build();

            colorList.add(colorResponseDto);
        }

        /*진단자의 퍼스널 컬러 타입 데이터 갖고 오기*/
        PersonalColorTypeDto personalColorTypeDto = new PersonalColorTypeDto(
                personalColorType.getId(),personalColorType.getPersonalColorTypeName(), colorList);


        List<PersonalColorTypeDto> personalColorTypeList = new ArrayList<>();
        personalColorTypeList.add(personalColorTypeDto);

        /*응답 데이터 만들기*/
        PersonalColorGroupResponseDto personalColorGroupResponseDto = new PersonalColorGroupResponseDto(
                personalColorGroup.getPersonalColorGroupName(),
                personalColorTypeList
        );

        return personalColorGroupResponseDto;
    }
}

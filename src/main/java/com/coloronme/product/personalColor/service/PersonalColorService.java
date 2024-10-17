package com.coloronme.product.personalColor.service;

import ch.qos.logback.core.util.COWArrayList;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.response.ColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorResponseDto;
import com.coloronme.product.personalColor.entity.PersonalColor;
import com.coloronme.product.personalColor.repository.PersonalColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonalColorService {

    private final PersonalColorRepository personalColorRepository;
    private final ConsultantRepository consultantRepository;

    public PersonalColorResponseDto getColorGroupList(int consultantId) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404));

        ColorGroup colorGroup = consultant.getColorGroup();

        PersonalColorResponseDto personalColorResponseDto = new PersonalColorResponseDto();

        if (colorGroup == ColorGroup.ALL) {
            List<ColorGroupResponseDto> colorGroupList = new ArrayList<>();
            /*1. PCCS */
            List<PersonalColor> personalColorList = personalColorRepository.findByColorGroup("PCCS");

            ColorGroupResponseDto colorGroupResponseDto = new ColorGroupResponseDto();
            colorGroupResponseDto.setColorGroupName("PCCS");

            for(PersonalColor personalColor : personalColorList) {







            }



            /*2. KS */


            ColorGroupResponseDto colorGroupResponseDto = colorGroup
            personalColorResponseDto.setColorGroups();


        } else {

        }




        return null;
    }
}

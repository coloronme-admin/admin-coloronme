package com.coloronme.product.personalColor.service;

import ch.qos.logback.core.util.COWArrayList;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.request.PersonalColorRequestDto;
import com.coloronme.product.personalColor.dto.response.ColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorDto;
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

    public ColorGroupResponseDto getColorGroupList(PersonalColorRequestDto personalColorRequestDto) {
        /*1. type 확인 all | pccs | ks */
        ColorGroup colorGroup = personalColorRequestDto.getType();
        System.out.println("personalColorRequestDto.getType() : " + personalColorRequestDto.getType());

        ColorGroupResponseDto colorGroupResponseDto = new ColorGroupResponseDto(colorGroup);

        /* type 이 all 인 경우*/
        if (colorGroup == ColorGroup.ALL) {
            /*return 값 받을 응답 객체*/

            System.out.println("ColorGroup is ALL");


/*            for(PersonalColor personalColor : personalColorList) {







            }



            *//*2. KS *//*


            ColorGroupResponseDto colorGroupResponseDto = colorGroup
            personalColorResponseDto.setColorGroups();


        } else {

        }


        *//* type 이 pccs 인 경우*//*
             *//* type 이 ks 인 경우*//*

             *//*2. group 확인 p, ltg, g, dkg, lt, sf, d, dk, b, s, dp, v *//*

        ColorGroupResponseDto colorGroupResponseDto = new ColorGroupResponseDto();

        PersonalColorResponseDto personalColorResponseDto = new PersonalColorResponseDto();*/


        }


        return colorGroupResponseDto;
    }

    private void getPersonalColorByGroup(ColorGroupResponseDto colorGroupResponseDto, ColorGroup colorGroup) {
        List<PersonalColorDto> personalColorList = personalColorRepository.findPersonalColorByGroup(colorGroup);
        System.out.println("personalColorName:" + personalColorList.get(1).getPersonalColorName());

        if(colorGroup == ColorGroup.PCCS) {
            for (PersonalColorDto personalColor : personalColorList) {
                ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                        personalColor.getR(), personalColor.getG(), personalColor.getB());

                switch (personalColor.getPersonalColorName()) {
                    case "페일":
                        colorGroupResponseDto.getPccs().getP().add(colorResponseData);
                        break;
                    case "라이트":
                        colorGroupResponseDto.getPccs().getLt().add(colorResponseData);
                        break;
                    case "브라이트":
                        colorGroupResponseDto.getPccs().getB().add(colorResponseData);
                        break;
                    case "비비드":
                        colorGroupResponseDto.getPccs().getV().add(colorResponseData);
                        break;
                    case "스트롱":
                        colorGroupResponseDto.getPccs().getS().add(colorResponseData);
                    case "소프트":
                        colorGroupResponseDto.getPccs().getSf().add(colorResponseData);
                        break;
                    case "덜":
                        colorGroupResponseDto.getPccs().getD().add(colorResponseData);
                        break;
                    case "딥":
                        colorGroupResponseDto.getPccs().getDp().add(colorResponseData);
                        break;
                    case "다크":
                        colorGroupResponseDto.getPccs().getDk().add(colorResponseData);
                        break;
                    case "라이트그레이쉬":
                        colorGroupResponseDto.getPccs().getLtg().add(colorResponseData);
                        break;
                    case "그레이쉬":
                        colorGroupResponseDto.getPccs().getG().add(colorResponseData);
                        break;
                    case "다크그레이쉬":
                        colorGroupResponseDto.getPccs().getDkg().add(colorResponseData);
                        break;
                    default:
                        break;
                }
            }
        }

        if(colorGroup == ColorGroup.KS) {
            for (PersonalColorDto personalColor : personalColorList) {
                ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                        personalColor.getR(), personalColor.getG(), personalColor.getB());

                switch (personalColor.getPersonalColorName()) {
                    case "화이티쉬":
                        colorGroupResponseDto.getKs().getWh().add(colorResponseData);
                        break;
                    case "페일":
                        colorGroupResponseDto.getPccs().getPl().add(colorResponseData);
                        break;
                    case "라이트":
                        colorGroupResponseDto.getPccs().getLt().add(colorResponseData);
                        break;
                    case "비비드":
                        colorGroupResponseDto.getPccs().getV().add(colorResponseData);
                        break;
                    case "라이트그레이쉬":
                        colorGroupResponseDto.getPccs().getLtg().add(colorResponseData);
                        break;
                    case "소프트":
                        colorGroupResponseDto.getPccs().getSf().add(colorResponseData);
                        break;
                    case "그레이쉬":
                        colorGroupResponseDto.getPccs().getG().add(colorResponseData);
                        break;
                    case "덜":
                        colorGroupResponseDto.getPccs().getD().add(colorResponseData);
                        break;
                    case "베이직":
                        colorGroupResponseDto.getPccs().getBs().add(colorResponseData);
                        break;
                    case "다크그레이쉬":
                        colorGroupResponseDto.getPccs().getDkg().add(colorResponseData);
                        break;
                    case "딥":
                        colorGroupResponseDto.getPccs().getDp().add(colorResponseData);
                        break;
                    case "다크":
                        colorGroupResponseDto.getPccs().getGk().add(colorResponseData);
                        break;
                    case "블랙키쉬":
                        colorGroupResponseDto.getPccs().getBk().add(colorResponseData);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}





package com.coloronme.product.personalColor.service;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.request.PersonalColorRequestDto;
import com.coloronme.product.personalColor.dto.response.ColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorDto;
import com.coloronme.product.personalColor.repository.PersonalColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonalColorService {

    private final PersonalColorRepository personalColorRepository;

    public ColorGroupResponseDto getColorGroupList(PersonalColorRequestDto personalColorRequestDto) {
        /*1. type 확인 all | pccs | ks */
        ColorGroup colorGroup = personalColorRequestDto.getType();
        System.out.println("personalColorRequestDto.getType() : " + personalColorRequestDto.getType());

        /*return 값 받을 응답 객체*/
        ColorGroupResponseDto colorGroupResponseDto = new ColorGroupResponseDto(colorGroup);

        /* type 이 all 인 경우*/
        if (colorGroup == ColorGroup.ALL) {
            System.out.println("ColorGroup is ALL");
            setPersonalColorByGroup(colorGroupResponseDto, ColorGroup.PCCS);
            setPersonalColorByGroup(colorGroupResponseDto, ColorGroup.KS);
        } else if (colorGroup == ColorGroup.PCCS) {
            setPersonalColorByGroup(colorGroupResponseDto, ColorGroup.PCCS);
        } else if (colorGroup == ColorGroup.KS) {
            setPersonalColorByGroup(colorGroupResponseDto, ColorGroup.KS);
        }

        return colorGroupResponseDto;
    }

    /*색상군별 조회*/
    private void setPersonalColorByGroup(ColorGroupResponseDto colorGroupResponseDto, ColorGroup colorGroup) {
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
                        colorGroupResponseDto.getKs().getPl().add(colorResponseData);
                        break;
                    case "라이트":
                        colorGroupResponseDto.getKs().getLt().add(colorResponseData);
                        break;
                    case "비비드":
                        colorGroupResponseDto.getKs().getV().add(colorResponseData);
                        break;
                    case "라이트그레이쉬":
                        colorGroupResponseDto.getKs().getLtg().add(colorResponseData);
                        break;
                    case "소프트":
                        colorGroupResponseDto.getKs().getSf().add(colorResponseData);
                        break;
                    case "그레이쉬":
                        colorGroupResponseDto.getKs().getG().add(colorResponseData);
                        break;
                    case "덜":
                        colorGroupResponseDto.getKs().getD().add(colorResponseData);
                        break;
                    case "베이직":
                        colorGroupResponseDto.getKs().getBs().add(colorResponseData);
                        break;
                    case "다크그레이쉬":
                        colorGroupResponseDto.getKs().getDkg().add(colorResponseData);
                        break;
                    case "딥":
                        colorGroupResponseDto.getKs().getDp().add(colorResponseData);
                        break;
                    case "다크":
                        colorGroupResponseDto.getKs().getGk().add(colorResponseData);
                        break;
                    case "블랙키쉬":
                        colorGroupResponseDto.getKs().getBk().add(colorResponseData);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}





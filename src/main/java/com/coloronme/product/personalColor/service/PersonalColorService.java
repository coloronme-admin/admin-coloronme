package com.coloronme.product.personalColor.service;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.request.PersonalColorRequestDto;
import com.coloronme.product.personalColor.dto.response.ColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorResponseDto;
import com.coloronme.product.personalColor.repository.PersonalColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonalColorService {

    private final PersonalColorRepository personalColorRepository;

    /*색상군 조회*/
    public ColorGroupResponseDto getColorGroupListByType(PersonalColorRequestDto personalColorRequestDto) {
        /*1. type 확인 all | pccs | ks */
        ColorGroup type = personalColorRequestDto.getColorGroupEnum();

        /*return 값 받을 응답 객체*/
        ColorGroupResponseDto colorGroupResponseDto = new ColorGroupResponseDto(type);

        if (type == ColorGroup.ALL) {
            setPersonalColorByType(colorGroupResponseDto, ColorGroup.PCCS);
            setPersonalColorByType(colorGroupResponseDto, ColorGroup.KS);
        } else if (type == ColorGroup.PCCS) {
            setPersonalColorByType(colorGroupResponseDto, ColorGroup.PCCS);
        } else if (type == ColorGroup.KS) {
            setPersonalColorByType(colorGroupResponseDto, ColorGroup.KS);
        }

        return colorGroupResponseDto;
    }

    /*색상군별 + 퍼스널 컬러 타입 조회*/
    public PersonalColorResponseDto getColorGroupListByTypeAndGroup(PersonalColorRequestDto personalColorRequestDto) {
        PersonalColorResponseDto personalColorResponseDto = new PersonalColorResponseDto();
        setPersonalColorByTypeAndGroup(personalColorResponseDto, personalColorRequestDto.getColorGroupEnum(), personalColorRequestDto.getGroup());
        return personalColorResponseDto;
    }

    private void setPersonalColorByType(ColorGroupResponseDto colorGroupResponseDto, ColorGroup type) {
        List<PersonalColorDto> personalColorList = personalColorRepository.findPersonalColorByType(type);

        if(type == ColorGroup.PCCS) {
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
        if(type == ColorGroup.KS) {
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
                        colorGroupResponseDto.getKs().getDk().add(colorResponseData);
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

    private void setPersonalColorByTypeAndGroup(PersonalColorResponseDto personalColorResponseDto, ColorGroup type, String group) {
        List<PersonalColorDto> personalColorList;
        /*공통*/
        switch (group) {
            case "p":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "페일");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setP(new ArrayList<>());
                    personalColorResponseDto.getP().add(colorResponseData);
                }
                break;
            case "lt":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "라이트");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setLt(new ArrayList<>());
                    personalColorResponseDto.getLt().add(colorResponseData);
                }
                break;
            case "b":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "브라이트");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setB(new ArrayList<>());
                    personalColorResponseDto.getB().add(colorResponseData);
                }
                break;
            case "v":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "비비드");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setV(new ArrayList<>());
                    personalColorResponseDto.getV().add(colorResponseData);
                }
                break;
            case "s":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "스트롱");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setS(new ArrayList<>());
                    personalColorResponseDto.getS().add(colorResponseData);
                }
                break;
            case "sf":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "소프트");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setSf(new ArrayList<>());
                    personalColorResponseDto.getSf().add(colorResponseData);
                }
                break;
            case "d":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "덜");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setD(new ArrayList<>());
                    personalColorResponseDto.getD().add(colorResponseData);
                }
                break;
            case "dp":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "딥");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setDp(new ArrayList<>());
                    personalColorResponseDto.getDp().add(colorResponseData);
                }
                break;
            case "dk":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "다크");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setDk(new ArrayList<>());
                    personalColorResponseDto.getDk().add(colorResponseData);
                }
                break;
            case "ltg":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "라이트그레이쉬");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setLtg(new ArrayList<>());
                    personalColorResponseDto.getLtg().add(colorResponseData);
                }
                break;
            case "g":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "그레이쉬");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setG(new ArrayList<>());
                    personalColorResponseDto.getG().add(colorResponseData);
                }
                break;
            case "dkg":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "다크그레이쉬");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setDkg(new ArrayList<>());
                    personalColorResponseDto.getDkg().add(colorResponseData);
                }
                break;
            case "wh":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "화이티쉬");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setWh(new ArrayList<>());
                    personalColorResponseDto.getWh().add(colorResponseData);
                }
                break;
            case "bs":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "베이직");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setBs(new ArrayList<>());
                    personalColorResponseDto.getBs().add(colorResponseData);
                }
                break;
            case "bk":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "블랙키쉬");
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.setBk(new ArrayList<>());
                    personalColorResponseDto.getBk().add(colorResponseData);
                }
                break;
            default:
                break;
        }
    }
}





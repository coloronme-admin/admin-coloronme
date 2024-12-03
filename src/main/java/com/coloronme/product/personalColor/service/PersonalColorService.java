package com.coloronme.product.personalColor.service;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.request.PersonalColorRequestDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorGroupResponseDto;
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
    public PersonalColorGroupResponseDto getColorGroupListByType(PersonalColorRequestDto personalColorRequestDto) {
        /*1. type 확인 all | pccs | ks */
        ColorGroup type = personalColorRequestDto.getColorGroupEnum();

        /*return 값 받을 응답 객체*/
        PersonalColorGroupResponseDto personalColorGroupResponseDto = new PersonalColorGroupResponseDto(type);

        if (type == ColorGroup.ALL) {
            setPersonalColorByType(personalColorGroupResponseDto, ColorGroup.PCCS);
            setPersonalColorByType(personalColorGroupResponseDto, ColorGroup.KS);
        } else if (type == ColorGroup.PCCS) {
            setPersonalColorByType(personalColorGroupResponseDto, ColorGroup.PCCS);
        } else if (type == ColorGroup.KS) {
            setPersonalColorByType(personalColorGroupResponseDto, ColorGroup.KS);
        }

        return personalColorGroupResponseDto;
    }

    /*색상군별 + 퍼스널 컬러 타입 조회*/
    public PersonalColorResponseDto getColorGroupListByTypeAndGroup(PersonalColorRequestDto personalColorRequestDto) {
        PersonalColorResponseDto personalColorResponseDto = new PersonalColorResponseDto();
        setPersonalColorByTypeAndGroup(personalColorResponseDto, personalColorRequestDto.getColorGroupEnum(), personalColorRequestDto.getGroup());
        return personalColorResponseDto;
    }

    private void setPersonalColorByType(PersonalColorGroupResponseDto personalColorGroupResponseDto, ColorGroup type) {
        List<PersonalColorDto> personalColorList = personalColorRepository.findPersonalColorByType(type);

        if(type == ColorGroup.PCCS) {
            for (PersonalColorDto personalColor : personalColorList) {
                ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                        personalColor.getR(), personalColor.getG(), personalColor.getB());
                switch (personalColor.getPersonalColorName()) {
                    case "페일":
                        personalColorGroupResponseDto.getPccs().getP().add(colorResponseData);
                        break;
                    case "라이트":
                        personalColorGroupResponseDto.getPccs().getLt().add(colorResponseData);
                        break;
                    case "브라이트":
                        personalColorGroupResponseDto.getPccs().getB().add(colorResponseData);
                        break;
                    case "비비드":
                        personalColorGroupResponseDto.getPccs().getV().add(colorResponseData);
                        break;
                    case "스트롱":
                        personalColorGroupResponseDto.getPccs().getS().add(colorResponseData);
                        break;
                    case "소프트":
                        personalColorGroupResponseDto.getPccs().getSf().add(colorResponseData);
                        break;
                    case "덜":
                        personalColorGroupResponseDto.getPccs().getD().add(colorResponseData);
                        break;
                    case "딥":
                        personalColorGroupResponseDto.getPccs().getDp().add(colorResponseData);
                        break;
                    case "다크":
                        personalColorGroupResponseDto.getPccs().getDk().add(colorResponseData);
                        break;
                    case "라이트그레이쉬":
                        personalColorGroupResponseDto.getPccs().getLtg().add(colorResponseData);
                        break;
                    case "그레이쉬":
                        personalColorGroupResponseDto.getPccs().getG().add(colorResponseData);
                        break;
                    case "다크그레이쉬":
                        personalColorGroupResponseDto.getPccs().getDkg().add(colorResponseData);
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
                        personalColorGroupResponseDto.getKs().getWh().add(colorResponseData);
                        break;
                    case "페일":
                        personalColorGroupResponseDto.getKs().getPl().add(colorResponseData);
                        break;
                    case "라이트":
                        personalColorGroupResponseDto.getKs().getLt().add(colorResponseData);
                        break;
                    case "비비드":
                        personalColorGroupResponseDto.getKs().getV().add(colorResponseData);
                        break;
                    case "라이트그레이쉬":
                        personalColorGroupResponseDto.getKs().getLtg().add(colorResponseData);
                        break;
                    case "소프트":
                        personalColorGroupResponseDto.getKs().getSf().add(colorResponseData);
                        break;
                    case "그레이쉬":
                        personalColorGroupResponseDto.getKs().getG().add(colorResponseData);
                        break;
                    case "덜":
                        personalColorGroupResponseDto.getKs().getD().add(colorResponseData);
                        break;
                    case "베이직":
                        personalColorGroupResponseDto.getKs().getBs().add(colorResponseData);
                        break;
                    case "다크그레이쉬":
                        personalColorGroupResponseDto.getKs().getDkg().add(colorResponseData);
                        break;
                    case "딥":
                        personalColorGroupResponseDto.getKs().getDp().add(colorResponseData);
                        break;
                    case "다크":
                        personalColorGroupResponseDto.getKs().getDk().add(colorResponseData);
                        break;
                    case "블랙키쉬":
                        personalColorGroupResponseDto.getKs().getBk().add(colorResponseData);
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
                personalColorResponseDto.setP(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getP().add(colorResponseData);
                }
                break;
            case "lt":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "라이트");
                personalColorResponseDto.setLt(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getLt().add(colorResponseData);
                }
                break;
            case "b":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "브라이트");
                personalColorResponseDto.setB(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getB().add(colorResponseData);
                }
                break;
            case "v":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "비비드");
                personalColorResponseDto.setV(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getV().add(colorResponseData);
                }
                break;
            case "s":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "스트롱");
                personalColorResponseDto.setS(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getS().add(colorResponseData);
                }
                break;
            case "sf":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "소프트");
                personalColorResponseDto.setSf(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getSf().add(colorResponseData);
                }
                break;
            case "d":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "덜");
                personalColorResponseDto.setD(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getD().add(colorResponseData);
                }
                break;
            case "dp":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "딥");
                personalColorResponseDto.setDp(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getDp().add(colorResponseData);
                }
                break;
            case "dk":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "다크");
                personalColorResponseDto.setDk(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getDk().add(colorResponseData);
                }
                break;
            case "ltg":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "라이트그레이쉬");
                personalColorResponseDto.setLtg(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getLtg().add(colorResponseData);
                }
                break;
            case "g":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "그레이쉬");
                personalColorResponseDto.setG(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getG().add(colorResponseData);
                }
                break;
            case "dkg":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "다크그레이쉬");
                personalColorResponseDto.setDkg(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getDkg().add(colorResponseData);
                }
                break;
            case "wh":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "화이티쉬");
                personalColorResponseDto.setWh(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getWh().add(colorResponseData);
                }
                break;
            case "bs":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "베이직");
                personalColorResponseDto.setBs(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getBs().add(colorResponseData);
                }
                break;
            case "bk":
                personalColorList = personalColorRepository.findPersonalColorByTypeAndGroup(type, "블랙키쉬");
                personalColorResponseDto.setBk(new ArrayList<>());
                for (PersonalColorDto personalColor : personalColorList) {
                    ColorResponseDto colorResponseData = new ColorResponseDto(personalColor.getColorId(), personalColor.getName(),
                            personalColor.getR(), personalColor.getG(), personalColor.getB());
                    personalColorResponseDto.getBk().add(colorResponseData);
                }
                break;
            default:
                break;
        }
    }
}





package com.coloronme.admin.domain.mainpage.service;

import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private final ConsultRepository consultRepository;

    /*퍼스널 컬러*/
    public List<ColorChartResponseDto> getUserDateByColor(int consultantId, MainPageRequestDto mainPageRequestDto) {

        List<Object[]> resultList = consultRepository.getUserDataByColor(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());

        List<ColorChartResponseDto> colorChartList = resultList.stream()
                .map(result -> new ColorChartResponseDto(
                        (String) result[0],
                        Integer.parseInt(String.valueOf(result[1])),
                        Double.parseDouble(String.valueOf(result[2]))
                ))
                .toList();

        int limit = mainPageRequestDto.getTop();

        if(colorChartList.size() > limit) {

            List<ColorChartResponseDto> colorChartResultList = new LinkedList<>();

            int count = 0;
            double percentage = 0;

            for (int i = 0; i < colorChartList.size(); i++) {
                if(i>=limit) {
                    count += colorChartList.get(i).getCount();
                    percentage += colorChartList.get(i).getPercentage();
                } else {
                    colorChartResultList.add(colorChartList.get(i));
                }
            }

            ColorChartResponseDto colorChartResponseDto = new ColorChartResponseDto();
            colorChartResponseDto.setName("ETC");
            colorChartResponseDto.setCount(count);
            colorChartResponseDto.setPercentage(percentage);

            colorChartResultList.add(colorChartResponseDto);

            return colorChartResultList;
        }
        return colorChartList;
    }

    /*요일 및 시간대*/
    public IntervalChartResponseDto getUserDataByInterval(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*성별*/
    public GenderChartResponseDto getUserDataByGender(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*연령대*/
    public AgeChartResponseDto getUserDataByAge(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*유입 채널*/
    public ChannelChartResponseDto getUserDataByChannel(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*6개월간 진단 추이*/
    public MonthChartResponseDto getUserDataByMonth(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }
}

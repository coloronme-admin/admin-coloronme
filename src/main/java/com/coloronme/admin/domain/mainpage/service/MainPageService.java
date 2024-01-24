package com.coloronme.admin.domain.mainpage.service;

import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private final ConsultRepository consultRepository;
    private final int WEEK = 7; /*월, 화, 수, 목, 금, 토, 일*/
    private final int HOUR = 24;

    /*퍼스널 컬러*/
    public List<ColorChartResponseDto> getUserDateByColor(int consultantId, MainPageRequestDto mainPageRequestDto) {

        List<ColorChartResponseDto> colorChartList = null;

        if(consultRepository.getUserDataCountByDate(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo())!=0) {
            /*DB에서 데이터 추출*/
            List<Object[]> resultList = consultRepository.getUserDataByColor(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());

            /*추출한 데이터를 DTO에 담기*/
            colorChartList = resultList.stream()
                    .map(result -> new ColorChartResponseDto(
                            (String) result[0],
                            Integer.parseInt(String.valueOf(result[1])),
                            Double.parseDouble(String.valueOf(result[2]))
                    ))
                    .toList();

            int limit = mainPageRequestDto.getTop();

            /*정렬 수보다 많으면 나머지 데이터는 ETC로 만듦*/
            if (colorChartList.size() > limit) {

                List<ColorChartResponseDto> colorChartResultList = new LinkedList<>();

                int count = 0;
                double percentage = 0;

                for (int i = 0; i < colorChartList.size(); i++) {
                    if (i >= limit) {
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
        }
        return colorChartList;
    }

    /*요일 및 시간대*/
    public List<IntervalChartResponseDto> getUserDataByInterval(int consultantId, MainPageRequestDto mainPageRequestDto) {

        List<Consult> consultListByDate = consultRepository.getUserDataByDate(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());

        if(!consultListByDate.isEmpty()) {
            List<IntervalChartResponseDto> intervalChartResultList = new LinkedList<>();

            DayOfWeek day = DayOfWeek.MONDAY;

            /*미리 MONDAY,, 요일 객체, 시간대 객체 설정*/
            for(int i=0 ; i<WEEK ; i++) {
                IntervalChartResponseDto intervalChartResponseDto = new IntervalChartResponseDto(HOUR);
                intervalChartResponseDto.setName(day.plus(i).toString());
                intervalChartResultList.add(intervalChartResponseDto);
            }

            for(int i=0 ; i<consultListByDate.size() ;i++ ){
                Consult consult = consultListByDate.get(i);
                LocalDateTime consultedDate = consult.getConsultedDate();
                List<IntervalChartResponseDto.IntervalChartData> intervalChartDataList;
                switch(consultedDate.getDayOfWeek().toString()) {
                    case "MONDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(0).getData();


                    }  case "TUESDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(1).getData();;

                    }  case "WEDNESDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(2).getData();;

                    }  case "THURSDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(3).getData();;

                    }  case "FRIDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(4).getData();;

                    }  case "SATURDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(5).getData();;

                    }  case "SUNDAY" -> {
                        intervalChartDataList = intervalChartResultList.get(6).getData();;
                    }

                    intervalChartDataList.get(consultedDate.getHour()).setCount(intervalChartDataList.get(consultedDate.getHour()).getCount()+1);
                }



            }
        }
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

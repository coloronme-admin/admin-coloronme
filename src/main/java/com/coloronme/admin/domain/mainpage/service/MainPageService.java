package com.coloronme.admin.domain.mainpage.service;

import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.admin.domain.mainpage.dto.PrincipalType;
import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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
                colorChartResponseDto.setPercentage(Math.round(percentage*10)/10.0);

                colorChartResultList.add(colorChartResponseDto);

                return colorChartResultList;
            }
        }
        return colorChartList;
    }

    /*요일 및 시간대*/
    public List<IntervalChartResponseDto> getUserDataByInterval(int consultantId, MainPageRequestDto mainPageRequestDto) {

        List<Consult> consultListByDate = consultRepository.getUserDataByDate(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());

        if (consultListByDate.isEmpty()) {
            return null;
        }

        List<IntervalChartResponseDto> intervalChartResultList = new LinkedList<>();
        DayOfWeek day = DayOfWeek.SUNDAY;

        if (mainPageRequestDto.getPrincipal().equalsIgnoreCase("DAY")) {
            /*미리 MONDAY,, 요일 객체, 시간대 객체 설정*/
            for (int i = 0; i < WEEK; i++) {
                IntervalChartResponseDto intervalChartResponseDto = new IntervalChartResponseDto(PrincipalType.DAY, HOUR);
                intervalChartResponseDto.setId(day.plus(i).toString());
                /*지정 기간의 요일별 상담 내용*/
                List<Consult> consultDataList = consultRepository.getUserDataByDay(consultantId, i,
                        mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());
                /*요일별 리스트를 시간대 별로 저장*/
                for (Consult consult : consultDataList) {
                    int time = consult.getConsultedDate().getHour();
                    intervalChartResponseDto.getData().get(time).setY(intervalChartResponseDto.getData().get(time).getY() + 1);
                }
                intervalChartResultList.add(intervalChartResponseDto);
            }
        } else if (mainPageRequestDto.getPrincipal().equalsIgnoreCase("TIME")) {

            for(int i=0 ; i<24 ; i++) {
                IntervalChartResponseDto intervalChartResponseDto = new IntervalChartResponseDto(PrincipalType.TIME, WEEK);
                intervalChartResponseDto.setId(String.valueOf(i));
                /*지정 기간의 시간별 상담 내용*/
                List<Consult> consultDataList = consultRepository.getUserDataByTime(consultantId, i,
                        mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());
                /*시간별 리스트를 요일별로 저장*/
                for (Consult consult : consultDataList) {
                    int date = consult.getConsultedDate().getDayOfWeek().getValue();
                    if(date == 7) {
                        date = 0;
                    }
                    intervalChartResponseDto.getData().get(date).setY(intervalChartResponseDto.getData().get(date).getY() + 1);
                }
                intervalChartResultList.add(intervalChartResponseDto);
            }
        }
        return intervalChartResultList;
    }

    /*성별*/
    public GenderChartResponseDto getUserDataByGender(int consultantId, MainPageRequestDto mainPageRequestDto) {

        List<Object[]> resultList = consultRepository.getUserDataByGender(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }

        int totalCount = resultList.size();
        int maleCount = 0;
        int femaleCount = 0;
        int unknownCount = 0;

        if (resultList != null) {
            for (Object[] result : resultList) {
                if (result != null) {
                    for (Object obj : result) {
                        if (obj == null) {
                            unknownCount++;
                            continue;
                        }
                        String gender = obj.toString();
                        if (gender.equals("MALE")) {
                            maleCount++;
                        } else if (gender.equals("FEMALE")) {
                            femaleCount++;
                        }
                    }
                } else {
                    unknownCount++;
                }
            }
        }
        int lastMaleCount = totalCount > 0 ? maleCount * 100 / totalCount : 0;
        int lastFemaleCount = totalCount > 0 ? femaleCount * 100 / totalCount : 0;
        int lastUnknownCount = totalCount > 0 ? unknownCount * 100 / totalCount : 0;

        return GenderChartResponseDto.builder()
                .male(lastMaleCount)
                .female(lastFemaleCount)
                .unknown(lastUnknownCount)
                .build();
    }

    /*연령대*/
    public AgeChartResponseDto getUserDataByAge(int consultantId, MainPageRequestDto mainPageRequestDto) {

        List<Object[]> resultList = consultRepository.getUserDataByAge(consultantId, mainPageRequestDto.getFrom(), mainPageRequestDto.getTo());
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }

        int totalCount = 0;
        int teenCount = 0;
        int twentiesCount = 0;
        int thirtiesCount = 0;
        int fortiesCount = 0;
        int fiftiesCount = 0;
        int sixtiesOverCount = 0;

        if (resultList != null) {
            for (Object[] result : resultList) {
                if (result != null) {
                    for (Object obj : result) {
                        int age = Integer.parseInt(obj.toString());
                        if (age >= 10 && age <= 19) {
                            teenCount++;
                        } else if (age >= 20 && age <= 29) {
                            twentiesCount++;
                        } else if (age >= 30 && age <= 39) {
                            thirtiesCount++;
                        } else if (age >= 40 && age <= 49) {
                            fortiesCount++;
                        } else if (age >= 50 && age <= 59) {
                            fiftiesCount++;
                        } else if (age >= 60 && age <= 99) {
                            sixtiesOverCount++;
                        }
                        totalCount++;
                    }
                }
            }
        }
        int lastTeenCount = totalCount > 0 ? teenCount * 100 / totalCount : 0;
        int lastTwentiesCount = totalCount > 0 ? twentiesCount * 100 / totalCount : 0;
        int lastThirtiesCount = totalCount > 0 ? thirtiesCount * 100 / totalCount : 0;
        int lastFortiesCount = totalCount > 0 ? fortiesCount * 100 / totalCount : 0;
        int lastFiftiesCount = totalCount > 0 ? fiftiesCount * 100 / totalCount : 0;
        int lastSixtiesOverCount = totalCount > 0 ? sixtiesOverCount * 100 / totalCount : 0;

        return AgeChartResponseDto.builder()
                .ten(lastTeenCount)
                .twenty(lastTwentiesCount)
                .thirty(lastThirtiesCount)
                .forty(lastFortiesCount)
                .fifty(lastFiftiesCount)
                .sixtyOver(lastSixtiesOverCount)
                .build();
    }

    /*유입 채널*/
    public ChannelChartResponseDto getUserDataByChannel(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*6개월간 진단 추이*/
    public MonthChartResponseDto getUserDataByMonth(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    public MonthChartResponseDto getUserDataByDay(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }
}

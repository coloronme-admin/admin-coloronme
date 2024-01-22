package com.coloronme.admin.domain.mainpage.service;

import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public class MainPageService {

    /*퍼스널 컬러*/
    public ColorChartResponseDto getUserDateByColor(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return new ColorChartResponseDto();
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

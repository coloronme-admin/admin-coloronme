package com.coloronme.admin.domain.mainpage.service;

import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.dto.response.MainPageResponseDto;
import org.springframework.stereotype.Service;

@Service
public class MainPageService {

    /*퍼스널 컬러*/
    public MainPageResponseDto getUserDateByColor(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*요일 및 시간대*/
    public MainPageResponseDto getUserDataByInterval(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*성별*/
    public MainPageResponseDto getUserDataByGender(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*연령대*/
    public MainPageResponseDto getUserDataByAge(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*유입 채널*/
    public MainPageResponseDto getUserDataByChannel(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }

    /*6개월간 진단 추이*/
    public MainPageResponseDto getUserDataByMonth(int consultantId, MainPageRequestDto mainPageRequestDto) {
        return null;
    }
}

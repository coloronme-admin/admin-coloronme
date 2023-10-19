package com.coloronme.admin.domain.mypage.service;

import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.domain.mypage.dto.MyPageRequestDto;
import com.coloronme.admin.domain.mypage.dto.MyPageResponseDto;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
@RequiredArgsConstructor
public class MyPageService {

    private final ConsultantRepository consultantRepository;

    private Consultant getConsultant(String email) {
        Consultant consultant = consultantRepository.findByEmail(email).orElseThrow(
                () -> new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404)
        );
        return consultant;
    }

    //마이 페이지 정보 불러오기
    @Transactional()
    public ResponseDto<MyPageResponseDto> getMyPage(String email) {

        Consultant consultant = getConsultant(email);

        return ResponseDto.success(
                MyPageResponseDto.builder()
                        .name(consultant.getName())
                        .company(consultant.getCompany())
                        .email(consultant.getEmail())
                        .build()
        );
    }

    //마이 페이지 수정
    @Transactional
    public ResponseDto<?> updateMyPage(MyPageRequestDto myPageRequestDto, String email) {
        Consultant consultant = getConsultant(email);

        consultant.update(myPageRequestDto);
        return ResponseDto.success("수정 완료");

    }
}

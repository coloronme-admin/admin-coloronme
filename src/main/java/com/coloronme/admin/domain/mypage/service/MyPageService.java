package com.coloronme.admin.domain.mypage.service;

import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.domain.mypage.dto.request.MyPageRequestDto;
import com.coloronme.admin.domain.mypage.dto.response.MyPageResponseDto;
import com.coloronme.admin.domain.mypage.dto.request.PasswordRequestDto;
import com.coloronme.admin.domain.mypage.dto.response.PasswordResponseDto;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
@RequiredArgsConstructor
public class MyPageService {

    private final ConsultantRepository consultantRepository;
    private final PasswordEncoder passwordEncoder;

    private Consultant getConsultant(String email) {
        Consultant consultant = consultantRepository.findByEmail(email).orElseThrow(
                () -> new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404)
        );
        return consultant;
    }

    //마이 페이지 정보 불러오기
    @Transactional(readOnly = true)
    public ResponseDto<MyPageResponseDto> getMyPage(String email) {
        Consultant consultant = getConsultant(email);

        return ResponseDto.status(
                MyPageResponseDto.builder()
                        .name(consultant.getName())
                        .company(consultant.getCompany())
                        .email(consultant.getEmail())
                        .build()
        );
    }

    //마이 페이지 수정
    @Transactional

    public ResponseDto<?> updateMyPage( MyPageRequestDto myPageRequestDto, String email) {
        Consultant consultant = getConsultant(email);

        if(myPageRequestDto.getName() != null) {
            consultant.updateName(myPageRequestDto);
        }
        if(myPageRequestDto.getCompany() != null) {
            consultant.updateCompany(myPageRequestDto);
        }
        if(myPageRequestDto.getEmail() != null) {
            consultant.updateEmail(myPageRequestDto);
        }
        return ResponseDto.status(
                MyPageResponseDto.builder()
                        .name(consultant.getName())
                        .company(consultant.getCompany())
                        .email(consultant.getEmail())
                        .build()
                    );
                }
    //비밀번호 변경
    @Transactional
    public ResponseDto<PasswordResponseDto> updatePassword(PasswordRequestDto passwordRequestDto, String email) {
        Consultant consultant = getConsultant(email);

        //기존 비밀번호 확인
        if (!passwordEncoder.matches(passwordRequestDto.getOldPassword(), consultant.getPassword())) {
            throw new RequestException(ErrorCode.OLD_PASSWORD_NOT_FOUND_404);
        }
        //새 비밀번호
        passwordRequestDto.setEncodedPwd(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
        consultant.updatePassword(passwordRequestDto);

        //새 비밀번호 확인
        if (!passwordEncoder.matches(passwordRequestDto.getNewPasswordConfirm(), consultant.getPassword())) {
            throw new RequestException(ErrorCode.PASSWORD_CONFIRM_BAD_REQUEST_400);
        }
        return ResponseDto.status(
                PasswordResponseDto.builder()
                        .newPassword(consultant.getPassword())
                        .build()
        );
    }
}

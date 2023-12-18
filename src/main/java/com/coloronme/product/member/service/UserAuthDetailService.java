package com.coloronme.product.member.service;

import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.member.entity.UserAuthDetail;
import com.coloronme.product.member.repository.MemberRepository;
import com.coloronme.product.member.repository.UserAuthDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthDetailService {

    private final UserAuthDetailRepository userAuthDetailRepository;
    private final MemberRepository memberRepository;

    public Member verifyUserQr(String uuid) {
        Optional<UserAuthDetail> userAuthDetail = userAuthDetailRepository.findByUuid(uuid);
        /*uuid로 조회가 되지 않을 경우*/
        if(userAuthDetail.isEmpty()){
            throw new RequestException(ErrorCode.UUID_NOT_FOUND_404);
        }

        UserAuthDetail userAuthDetailData = userAuthDetail.get();

        /*기간이 만료된 qr일 경우*/
        String userExpiredAtData = userAuthDetailData.getUuidExpiredAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        LocalDateTime userExpiredAt = LocalDateTime.parse(userExpiredAtData, formatter);
        int compare = LocalDateTime.now().compareTo(userExpiredAt);

        if(compare > 0) {
            throw new RequestException(ErrorCode.QR_EXPIRED_AT_BAD_REQUEST_400);
        }

        Optional<Member> member = memberRepository.findByUserAuthDetailId(userAuthDetailData.getId());

        if(member.isEmpty()) {
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        return member.get();
    }
}

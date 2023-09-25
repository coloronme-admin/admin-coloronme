package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.user.entity.Member;
import com.coloronme.admin.domain.personalColor.entity.PersonalColor;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.admin.domain.personalColor.repository.PersonalColorRepository;
import com.coloronme.admin.domain.user.repository.MemberRepository;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConsultService {
    private final MemberRepository memberRepository;
    private final ConsultRepository consultUserRepository;
    private final ConsultantRepository consultantRepository;
    private final PersonalColorRepository personalColorRepository;
    private final ConsultRepository consultRepository;

    @Transactional
    public void registerConsultUser(String consultantEmail, Long userId, ConsultRequestDto consultRequestDto) {
        Optional<Member> user = memberRepository.findById(userId);
        if (user.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if (consultant.isEmpty()) {
            log.error("CONSULTANT_NOT_FOUND.");
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        Optional<PersonalColor> personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId());
        if (personalColor.isEmpty()) {
            log.error("PERSONAL COLOR NOT FOUND.");
            throw new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404);
        }

        Member memberData = user.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
        Consult consult = new Consult(consultant.get().getId(), userId, consultRequestDto);
        consultUserRepository.save(consult);
    }

    public ConsultUserResponseDto selectConsultUserByUserId(Long userId) {
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consult> consult = consultRepository.findById(userId);
        if(consult.isEmpty()) {
            log.error("CONSULT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULT_NOT_FOUND_404);
        }

        Member memberData = member.get();
        Consult consultData = consult.get();

        return ConsultUserResponseDto.builder()
                .nickname(memberData.getNickname())
                .email(memberData.getEmail())
                .personalDate(consultData.getConsultDate())
                .age(memberData.getAge())
                .gender(memberData.getGender())
                .consultContent(consultData.getConsultContent())
                .build();
    }


}
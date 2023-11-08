package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.personalColor.entity.PersonalColor;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.product.personalColor.repository.PersonalColorRepository;
import com.coloronme.product.member.repository.MemberRepository;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ConsultService {
    private final MemberRepository memberRepository;
    private final ConsultRepository consultUserRepository;
    private final ConsultantRepository consultantRepository;
    private final PersonalColorRepository personalColorRepository;
    private final ConsultRepository consultRepository;

    public void registerConsultUser(String consultantEmail, int userId, ConsultRequestDto consultRequestDto) {
        Optional<Member> user = memberRepository.findById(userId);
        if (user.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if (consultant.isEmpty()) {
            log.error("CONSULTANT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        Optional<PersonalColor> personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId());
        if (personalColor.isEmpty()) {
            log.error("PERSONAL COLOR NOT FOUND.");
            throw new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404);
        }

        Member memberData = user.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
        Consult consult = new Consult(consultant.get().getId(), memberData.getId(), personalColor.get().getId(), consultRequestDto);

        consultUserRepository.save(consult);
    }

    public ConsultUserResponseDto selectConsultUserByUserId(int userId, String consultantEmail) {
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if(consultant.isEmpty()){
            log.error("CONSULTANT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        /*Optional<Consult> consult = consultRepository.findByMemberId(memberId);*/
        Optional<Consult> consult = consultRepository.findByMemberIdAndConsultantId(userId, consultant.get().getId());
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
                .personalColorId(consultData.getPersonalColorId())
                .consultContent(consultData.getConsultContent())
                .consultDrawing(consultData.getConsultDrawing())
                .build();
    }

    public List<ConsultUserResponseDto> selectConsultUserList(String consultantEmail) {
        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if(consultant.isEmpty()) {
            log.error("CONSULTANT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        Consultant consultantData = consultant.get();
        List<Consult> consultList = consultRepository.findAllByConsultantId(consultantData.getId());

        List<ConsultUserResponseDto> consultUserList = new LinkedList<>();
        for(Consult consult : consultList) {
            Optional<Member> member = memberRepository.findById(consult.getMemberId());

            if(member.isEmpty()) {
                log.error("USER NOT FOUND.");
                throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
            }

            Optional<PersonalColor> personalColor = personalColorRepository.findById(consult.getPersonalColorId());
            if (personalColor.isEmpty()) {
                log.error("PERSONAL COLOR NOT FOUND.");
                throw new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404);
            }

            Member memberData = member.get();
            PersonalColor personalColorData = personalColor.get();
            ConsultUserResponseDto consultUserResponseDto = ConsultUserResponseDto.builder()
                    .nickname(memberData.getNickname())
                    .email(memberData.getEmail())
                    .personalDate(consult.getConsultDate())
                    .personalColorId(personalColorData.getId())
                    .age(memberData.getAge())
                    .gender(memberData.getGender())
                    .consultContent(consult.getConsultContent())
                    .consultDrawing(consult.getConsultDrawing())
                    .build();

            consultUserList.add(consultUserResponseDto);
        }
        return consultUserList;
    }

    public void updateConsultUser(String consultantEmail, int userId, ConsultRequestDto consultRequestDto) {
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if(consultant.isEmpty()){
            log.error("CONSULTANT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        Optional<Consult> consult = consultRepository.findByMemberIdAndConsultantId(userId, consultant.get().getId());
        if(consult.isEmpty()) {
            log.error("CONSULT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULT_NOT_FOUND_404);
        }

        Optional<PersonalColor> personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId());
        if (personalColor.isEmpty()) {
            log.error("PERSONAL COLOR NOT FOUND.");
            throw new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404);
        }

        Consult consultData = consult.get();
        consultData.setPersonalColorId(personalColor.get().getId());
        consultData.setConsultContent(consultRequestDto.getConsultContent());
        consultData.setConsultDrawing(consultRequestDto.getConsultDrawing());

        Member memberData = member.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
    }
}
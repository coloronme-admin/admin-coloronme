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

import java.time.LocalDateTime;
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

    public void registerConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {
        Optional<Member> user = memberRepository.findById(userId);
        if (user.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<PersonalColor> personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId());
        if (personalColor.isEmpty()) {
            log.error("PERSONAL COLOR NOT FOUND.");
            throw new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404);
        }

        Member memberData = user.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
        Consult consult = new Consult(consultantId, memberData.getId(), personalColor.get().getId(), consultRequestDto);

        consultUserRepository.save(consult);
    }

    public ConsultUserResponseDto selectConsultUserByUserId(int userId, int consultantId) {
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        /*Optional<Consult> consult = consultRepository.findByMemberId(memberId);*/
        Optional<Consult> consult = consultRepository.findByMemberIdAndConsultantId(userId, consultantId);
        if(consult.isEmpty()) {
            log.error("CONSULT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULT_NOT_FOUND_404);
        }

        Member memberData = member.get();
        Consult consultData = consult.get();

        return ConsultUserResponseDto.builder()
                .nickname(memberData.getNickname())
                .email(memberData.getEmail())
                .personalDate(consultData.getConsultedDate())
                .age(memberData.getAge())
                .gender(memberData.getGender())
                .personalColorId(consultData.getPersonalColorId())
                .consultedContent(consultData.getConsultedContent())
                .consultedDrawing(consultData.getConsultedDrawing())
                .build();
    }

    public List<ConsultUserResponseDto> selectConsultUserList(int consultantId) {

        List<Consult> consultList = consultRepository.findAllByConsultantId(consultantId);

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
                    .personalDate(consult.getConsultedDate())
                    .personalColorId(personalColorData.getId())
                    .age(memberData.getAge())
                    .gender(memberData.getGender())
                    .consultedContent(consult.getConsultedContent())
                    .consultedDrawing(consult.getConsultedDrawing())
                    .build();

            consultUserList.add(consultUserResponseDto);
        }
        return consultUserList;
    }

    public void updateConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consult> consult = consultRepository.findByMemberIdAndConsultantId(userId, consultantId);
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
        consultData.setConsultedContent(consultRequestDto.getConsultedContent());
        consultData.setConsultedDrawing(consultRequestDto.getConsultedDrawing());

        Member memberData = member.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
    }

    public ConsultUserResponseDto verifyUserQr(int consultantId, Member member) {
        Optional<Consult> consult = consultRepository.findByMemberIdAndConsultantId(member.getId(), consultantId);

        ConsultUserResponseDto consultUserResponseDto;

        /*이전 진단 내역이 없는 경우에는 진단 내용을 null 값으로 보냄*/
        if(consult.isEmpty()) {
            consultUserResponseDto = ConsultUserResponseDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .personalDate(null)
                    .personalColorId(1)
                    .age(member.getAge())
                    .gender(member.getGender())
                    .consultedContent(null)
                    .consultedDrawing(null)
                    .build();

        /*이전 진단 내역이 있는 경우에는 이전 내용을 같이 보내줌*/
        } else {

            Consult consultData = consult.get();

            consultUserResponseDto = ConsultUserResponseDto.builder()
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .personalDate(consultData.getConsultedDate())
                    .personalColorId(consultData.getPersonalColorId())
                    .age(member.getAge())
                    .gender(member.getGender())
                    .consultedContent(consultData.getConsultedContent())
                    .consultedDrawing(consultData.getConsultedDrawing())
                    .build();
        }

        return consultUserResponseDto;
    }
}
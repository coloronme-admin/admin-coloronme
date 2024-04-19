package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.personalColor.entity.PersonalColor;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.product.personalColor.repository.PersonalColorRepository;
import com.coloronme.product.member.repository.MemberRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ConsultService {
    private final MemberRepository memberRepository;
    private final ConsultRepository consultUserRepository;
    private final PersonalColorRepository personalColorRepository;
    private final ConsultRepository consultRepository;

    public ConsultUserResponseDto registerConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {
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

        Optional<Consult> consult = consultRepository.findByMemberId(userId);

        Consult consultData;

        /*진단 정보를 처음 등록하는 경우*/
        if(consult.isEmpty()) {
            memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
            /*uuid 등록*/
            UUID uuid = UUID.randomUUID();
            consultRequestDto.setUuid(uuid.toString());
            consultData = new Consult(consultantId, userId, personalColor.get().getId(), consultRequestDto);
            consultUserRepository.save(consultData);
        } else {
            /*진단 정보가 이미 있는 경우 수정 작업으로 변경*/
            consultData = consult.get();
            consultData.setPersonalColorId(personalColor.get().getId());
            consultData.setConsultedDate(consultRequestDto.getConsultedDate());
            consultData.setConsultedContent(consultRequestDto.getConsultedContent());
            consultData.setConsultedDrawing(consultRequestDto.getConsultedDrawing());
            consultData.setUpdatedAt(LocalDateTime.now());
            memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
        }

        return ConsultUserResponseDto.builder()
                .memberId(consultData.getMemberId())
                .nickname(memberData.getNickname())
                .profileImageUrl(memberData.getProfileImageUrl())
                .email(memberData.getEmail())
                .consultedDate(consultData.getConsultedDate())
                .age(memberData.getAge())
                .genderEnum(memberData.getGender())
                .personalColorId(consultData.getPersonalColorId())
                .consultedContent(consultData.getConsultedContent())
                .consultedDrawing(consultData.getConsultedDrawing())
                .build();
    }

    public ConsultUserResponseDto selectConsultUserByUserId(int userId, int consultantId) {
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

        Member memberData = member.get();
        Consult consultData = consult.get();

        return ConsultUserResponseDto.builder()
                .memberId(consultData.getMemberId())
                .nickname(memberData.getNickname())
                .profileImageUrl(memberData.getProfileImageUrl())
                .email(memberData.getEmail())
                .consultedDate(consultData.getConsultedDate())
                .age(memberData.getAge())
                .genderEnum(memberData.getGender())
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

            Member memberData = member.get();
            ConsultUserResponseDto consultUserResponseDto = ConsultUserResponseDto.builder()
                    .memberId(consult.getMemberId())
                    .nickname(memberData.getNickname())
                    .email(memberData.getEmail())
                    .consultedDate(consult.getConsultedDate())
                    .profileImageUrl(memberData.getProfileImageUrl())
                    .personalColorId(consult.getPersonalColorId())
                    .age(memberData.getAge())
                    .genderEnum(memberData.getGender())
                    .consultedContent(consult.getConsultedContent())
                    .consultedDrawing(consult.getConsultedDrawing())
                    .build();

            consultUserList.add(consultUserResponseDto);
        }
        return consultUserList;
    }

    public ConsultUserResponseDto updateConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consult> consult = consultRepository.findByMemberId(userId);
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
        consultData.setUpdatedAt(LocalDateTime.now());

        Member memberData = member.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());

        return ConsultUserResponseDto.builder()
                .memberId(consultData.getMemberId())
                .nickname(memberData.getNickname())
                .email(memberData.getEmail())
                .consultedDate(consultData.getConsultedDate())
                .profileImageUrl(memberData.getProfileImageUrl())
                .personalColorId(consultData.getPersonalColorId())
                .age(memberData.getAge())
                .genderEnum(memberData.getGender())
                .consultedContent(consultData.getConsultedContent())
                .consultedDrawing(consultData.getConsultedDrawing())
                .build();
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
                    .profileImageUrl(member.getProfileImageUrl())
                    .consultedDate(null)
                    .personalColorId(1)
                    .age(member.getAge())
                    .genderEnum(member.getGender())
                    .consultedContent(null)
                    .consultedDrawing(null)
                    .build();

        /*이전 진단 내역이 있는 경우에는 이전 내용을 같이 보내줌*/
        } else {

            Consult consultData = consult.get();

            consultUserResponseDto = ConsultUserResponseDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .profileImageUrl(member.getProfileImageUrl())
                    .consultedDate(consultData.getConsultedDate())
                    .personalColorId(consultData.getPersonalColorId())
                    .age(member.getAge())
                    .genderEnum(member.getGender())
                    .consultedContent(consultData.getConsultedContent())
                    .consultedDrawing(consultData.getConsultedDrawing())
                    .build();
        }

        return consultUserResponseDto;
    }
}
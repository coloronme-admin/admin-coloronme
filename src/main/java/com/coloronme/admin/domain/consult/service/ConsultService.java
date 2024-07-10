package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ColorRequestDto;
import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ColorResponseDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.entity.ConsultColor;
import com.coloronme.admin.domain.consult.repository.ConsultColorRepository;
import com.coloronme.product.color.entity.Color;
import com.coloronme.product.color.repository.ColorRepository;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.personalColor.entity.PersonalColor;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.product.personalColor.entity.PersonalColorType;
import com.coloronme.product.personalColor.repository.PersonalColorRepository;
import com.coloronme.product.member.repository.MemberRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.product.personalColor.repository.PersonalColorTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ConsultService {
    private final MemberRepository memberRepository;
    private final ConsultRepository consultUserRepository;
    private final PersonalColorRepository personalColorRepository;
    private final ConsultRepository consultRepository;
    private final PersonalColorTypeRepository personalColorTypeRepository;
    private final ColorRepository colorRepository;
    private final ConsultColorRepository consultColorRepository;

    @Transactional
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

        Optional<PersonalColorType> personalColorType = personalColorTypeRepository.findById(consultRequestDto.getPersonalColorTypeId());
        if (personalColorType.isEmpty()) {
            log.error("PERSONAL COLOR TYPE NOT FOUND.");
            throw new RequestException(ErrorCode.PERSONAL_COLOR_TYPE_NOT_FOUND_404);
        }

        Member memberData = user.get();

        Optional<Consult> consult = consultRepository.findByMemberId(userId);

        Consult consultData;

        List<ColorResponseDto> colorList = new ArrayList<>();

        /*진단 정보를 처음 등록하는 경우*/
        if(consult.isEmpty()) {
            memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
            /*uuid 등록*/
            UUID uuid = UUID.randomUUID();
            consultRequestDto.setUuid(uuid.toString());
            consultData = new Consult(consultantId, userId, personalColor.get().getId(), consultRequestDto);
            System.out.println("여기 거치나?1");

            Consult createdConult = consultUserRepository.save(consultData);

            for(ColorRequestDto colorRequestDto : consultRequestDto.getColors()) {

                Optional<Color> color = colorRepository.findById(colorRequestDto.getColorId());
                if(color.isEmpty()) {
                    throw new RequestException(ErrorCode.COLOR_NOT_FOUND_404);
                }

                Color colorData = color.get();

                ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                        .colorId(colorData.getId())
                        .name(colorData.getName())
                        .r(colorData.getR())
                        .g(colorData.getG())
                        .b(colorData.getB())
                        .build();

                colorList.add(colorResponseDto);

                ConsultColor consultColor = ConsultColor.builder()
                        .colorId(color.get().getId())
                        .consultId(createdConult.getId())
                        .build();

                consultColorRepository.save(consultColor);
            }


        } else {
            System.out.println("여기 거치나?2");
            /*진단 정보가 이미 있는 경우 수정 작업으로 변경*/
            consultData = consult.get();
            consultData.setPersonalColorId(personalColor.get().getId());
            consultData.setConsultedDate(consultRequestDto.getConsultedDate());
            consultData.setConsultedContent(consultRequestDto.getConsultedContent());
            consultData.setConsultedDrawing(consultRequestDto.getConsultedDrawing());
            consultData.setConsultedFile(consultRequestDto.getConsultedFile());
            memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
            consultData.setPersonalColorTypeId(consultRequestDto.getPersonalColorTypeId());
            consultData.setUpdatedAt(LocalDateTime.now());

            /*기존 ConsultColor Data는 삭제후 재생성*/
            List<ConsultColor> consultColors = consultColorRepository.findByConsultId(consult.get().getId());
            consultColorRepository.deleteAllInBatch(consultColors);

            /*수정된 데이터로 재생성*/
            for(ColorRequestDto colorRequestDto : consultRequestDto.getColors()) {

                Optional<Color> color = colorRepository.findById(colorRequestDto.getColorId());
                if(color.isEmpty()) {
                    throw new RequestException(ErrorCode.COLOR_NOT_FOUND_404);
                }

                Color colorData = color.get();

                ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                        .colorId(colorData.getId())
                        .name(colorData.getName())
                        .r(colorData.getR())
                        .g(colorData.getG())
                        .b(colorData.getB())
                        .build();

                colorList.add(colorResponseDto);

                ConsultColor consultColor = ConsultColor.builder()
                        .colorId(color.get().getId())
                        .consultId(consult.get().getId())
                        .build();

                consultColorRepository.save(consultColor);
            }
        }

        return createConsultUserResponseDto(consultData, memberData, colorList);
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

        return createConsultUserResponseDto(consultData, memberData, null);
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
            ConsultUserResponseDto consultUserResponseDto = createConsultUserResponseDto(consult, memberData, null);

            consultUserList.add(consultUserResponseDto);
        }
        return consultUserList;
    }

    @Transactional
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
        consultData.setConsultedFile(consultRequestDto.getConsultedFile());
        consultData.setUpdatedAt(LocalDateTime.now());

        Member memberData = member.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());

        return createConsultUserResponseDto(consultData, memberData, null);
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
                    .consultedFile(null)
                    .build();

        /*이전 진단 내역이 있는 경우에는 이전 내용을 같이 보내줌*/
        } else {

            Consult consultData = consult.get();

            consultUserResponseDto = createConsultUserResponseDto(consultData, member, null);
        }

        return consultUserResponseDto;
    }

    public ConsultUserResponseDto selectConsultUserByUuid(String uuid) {
        Optional<Consult> consult = consultRepository.findByUuid(uuid);

        if(consult.isEmpty()) {
            log.error("CONSULT NOT FOUND.");
            throw new RequestException(ErrorCode.CONSULT_NOT_FOUND_404);
        }

        Consult consultData = consult.get();

        Optional<Member> member = memberRepository.findById(consultData.getMemberId());
        if(member.isEmpty()) {
            log.error("USER NOT FOUND.");
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Member memberData = member.get();

        return createConsultUserResponseDto(consultData, memberData, null);
    }

    private ConsultUserResponseDto createConsultUserResponseDto (Consult consultData, Member memberData, List<ColorResponseDto> colorList) {
        return ConsultUserResponseDto.builder()
                .memberId(consultData.getMemberId())
                .nickname(memberData.getNickname())
                .email(memberData.getEmail())
                .consultedDate(consultData.getConsultedDate())
                .profileImageUrl(memberData.getProfileImageUrl())
                .personalColorId(consultData.getPersonalColorId())
                .age(memberData.getAge())
                .colorList(colorList)
                .genderEnum(memberData.getGender())
                .consultedContent(consultData.getConsultedContent())
                .consultedDrawing(consultData.getConsultedDrawing())
                .consultedFile(consultData.getConsultedFile())
                .uuid(consultData.getUuid())
                .build();
    }
}
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

        Optional<Consult> consult = consultRepository.findByMemberId(userId);

        /*진단 정보를 처음 등록하는 경우*/
        if(consult.isEmpty()) {

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

            memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());

            /*uuid 생성*/
            UUID uuid = UUID.randomUUID();
            consultRequestDto.setUuid(uuid.toString());

            Consult consultData = new Consult(consultantId, userId, personalColor.get().getId(), consultRequestDto);

            List<ConsultColor> consultColors = new ArrayList<>();

            List<ColorResponseDto> colorList = new ArrayList<>();

            for(ColorRequestDto colorRequestDto : consultRequestDto.getColors()) {

                Optional<Color> color = colorRepository.findById(colorRequestDto.getColorId());
                if(color.isEmpty()) {
                    throw new RequestException(ErrorCode.COLOR_NOT_FOUND_404);
                }

                Color colorData = color.get();

                ConsultColor consultColor = new ConsultColor();
                consultColor.setConsult(consultData);
                consultColor.setColor(colorData);

                consultColors.add(consultColor);

                ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                        .colorId(colorData.getId())
                        .name(colorData.getName())
                        .r(colorData.getR())
                        .g(colorData.getG())
                        .b(colorData.getB())
                        .build();

                colorList.add(colorResponseDto);
            }

            consultData.setConsultColors(consultColors);

            /*consult 등록*/
            consultUserRepository.save(consultData);

            return createConsultUserResponseDto(consultData, memberData, colorList);

        } else {
            /*진단 정보가 이미 있는 경우 수정*/
            return updateConsultUser(userId, consultRequestDto);
        }
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

        List<ColorResponseDto> colorList = new ArrayList<>();

        for(ConsultColor consultColor : consultData.getConsultColors()) {

            Optional<Color> color = colorRepository.findById(consultColor.getColor().getId());
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
        }

        return createConsultUserResponseDto(consultData, memberData, colorList);
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

            List<ColorResponseDto> colorList = new ArrayList<>();

            for(ConsultColor consultColor : consult.getConsultColors()) {

                Optional<Color> color = colorRepository.findById(consultColor.getColor().getId());
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
            }

            ConsultUserResponseDto consultUserResponseDto = createConsultUserResponseDto(consult, memberData, colorList);

            consultUserList.add(consultUserResponseDto);
        }
        return consultUserList;
    }

    @Transactional
    public ConsultUserResponseDto updateConsultUser(int userId, ConsultRequestDto consultRequestDto) {
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

        /*기존 ConsultColor Data는 삭제후 재생성*/
        List<ConsultColor> consultColors = consultColorRepository.findByConsultId(consult.get().getId());
        consultColorRepository.deleteAllInBatch(consultColors);

        List<ConsultColor> newConsultColors = new ArrayList<>();
        List<ColorResponseDto> colorList = new ArrayList<>();

        for(ColorRequestDto colorRequestDto : consultRequestDto.getColors()) {
            Optional<Color> color = colorRepository.findById(colorRequestDto.getColorId());
            if(color.isEmpty()) {
                throw new RequestException(ErrorCode.COLOR_NOT_FOUND_404);
            }

            Color colorData = color.get();

            ConsultColor consultColor = new ConsultColor();
            consultColor.setConsult(consultData);
            consultColor.setColor(colorData);

            newConsultColors.add(consultColor);

            ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                    .colorId(colorData.getId())
                    .name(colorData.getName())
                    .r(colorData.getR())
                    .g(colorData.getG())
                    .b(colorData.getB())
                    .build();

            colorList.add(colorResponseDto);
        }

        consultData.setConsultColors(newConsultColors);
        consultRepository.save(consultData);

        return createConsultUserResponseDto(consultData, memberData, colorList);
    }

    public ConsultUserResponseDto verifyUserQr(int consultantId, Member member) {
        Optional<Consult> consult = consultRepository.findByMemberIdAndConsultantId(member.getId(), consultantId);

        ConsultUserResponseDto consultUserResponseDto;

        /*이전 진단 내역이 없는 경우에는 진단 내용을 null 값으로 보냄*/
        if(consult.isEmpty()) {
            consultUserResponseDto = createConsultUserResponseDto(null, member, null);

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

        ConsultUserResponseDto consultUserResponseDto = null;

        if(consultData == null) {
            consultUserResponseDto = ConsultUserResponseDto.builder()
                    .memberId(memberData.getId())
                    .nickname(memberData.getNickname())
                    .email(memberData.getEmail())
                    .profileImageUrl(memberData.getProfileImageUrl())
                    .consultedDate(null)
                    .personalColorId(1)
                    .age(memberData.getAge())
                    .colorList(null)
                    .genderEnum(memberData.getGender())
                    .consultedContent(null)
                    .consultedDrawing(null)
                    .consultedFile(null)
                    .build();
        } else {
            consultUserResponseDto = ConsultUserResponseDto.builder()
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

        return consultUserResponseDto;
    }
}
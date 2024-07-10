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

    @Transactional
    public ConsultUserResponseDto registerConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {

        Optional<Consult> consult = consultRepository.findByMemberId(userId);

        /*진단 정보를 처음 등록하는 경우*/
        if(consult.isEmpty()) {
            /*유효성 검사*/
            Member member = memberRepository.findById(userId)
                    .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

            PersonalColor personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404));

            personalColorTypeRepository.findById(consultRequestDto.getPersonalColorTypeId())
                    .orElseThrow(() -> new RequestException(ErrorCode.PERSONAL_COLOR_TYPE_NOT_FOUND_404));

            member.setPersonalColorId(consultRequestDto.getPersonalColorId());

            /*uuid 생성*/
            UUID uuid = UUID.randomUUID();
            consultRequestDto.setUuid(uuid.toString());

            Consult consultData = new Consult(consultantId, userId, personalColor.getId(), consultRequestDto);

            List<ConsultColor> consultColors = new ArrayList<>();
            List<ColorResponseDto> colorList = new ArrayList<>();

            for(ColorRequestDto colorRequestDto : consultRequestDto.getColors()) {

                Color color = colorRepository.findById(colorRequestDto.getColorId())
                        .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));

                ConsultColor consultColor = new ConsultColor();
                consultColor.setConsult(consultData);
                consultColor.setColor(color);

                consultColors.add(consultColor);

                ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                        .colorId(color.getId())
                        .name(color.getName())
                        .r(color.getR())
                        .g(color.getG())
                        .b(color.getB())
                        .build();

                colorList.add(colorResponseDto);
            }

            consultData.setConsultColors(consultColors);

            /*consult 등록*/
            consultUserRepository.save(consultData);

            return createConsultUserResponseDto(consultData, member, colorList);

        } else {
            /*진단 정보가 이미 있는 경우 수정*/
            return updateConsultUser(userId, consultRequestDto);
        }
    }

    public ConsultUserResponseDto selectConsultUserByUserId(int userId) {
        /*유효성 검사*/
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        Consult consult = consultRepository.findByMemberId(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULT_NOT_FOUND_404));

        List<ColorResponseDto> colorList = new ArrayList<>();

        for(ConsultColor consultColor : consult.getConsultColors()) {

            Color color = colorRepository.findById(consultColor.getColor().getId())
                    .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));

            ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                    .colorId(color.getId())
                    .name(color.getName())
                    .r(color.getR())
                    .g(color.getG())
                    .b(color.getB())
                    .build();

            colorList.add(colorResponseDto);
        }

        return createConsultUserResponseDto(consult, member, colorList);
    }

    public List<ConsultUserResponseDto> selectConsultUserList(int consultantId) {

        List<Consult> consultList = consultRepository.findAllByConsultantId(consultantId);

        List<ConsultUserResponseDto> consultUserList = new LinkedList<>();
        for(Consult consult : consultList) {
            /*유효성 검사*/
            Member member = memberRepository.findById(consult.getMemberId())
                    .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

            List<ColorResponseDto> colorList = new ArrayList<>();

            for(ConsultColor consultColor : consult.getConsultColors()) {

                Color color = colorRepository.findById(consultColor.getColor().getId())
                        .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));

                ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                        .colorId(color.getId())
                        .name(color.getName())
                        .r(color.getR())
                        .g(color.getG())
                        .b(color.getB())
                        .build();

                colorList.add(colorResponseDto);
            }

            ConsultUserResponseDto consultUserResponseDto = createConsultUserResponseDto(consult, member, colorList);

            consultUserList.add(consultUserResponseDto);
        }
        return consultUserList;
    }

    @Transactional
    public ConsultUserResponseDto updateConsultUser(int userId, ConsultRequestDto consultRequestDto) {
        /*유효성 검사*/
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        PersonalColor personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId())
                .orElseThrow(() -> new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404));

        Consult consult = consultRepository.findByMemberId(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULT_NOT_FOUND_404));

        consult.setPersonalColorId(personalColor.getId());
        consult.setConsultedContent(consultRequestDto.getConsultedContent());
        consult.setConsultedDrawing(consultRequestDto.getConsultedDrawing());
        consult.setConsultedFile(consultRequestDto.getConsultedFile());
        consult.setUpdatedAt(LocalDateTime.now());

        member.setPersonalColorId(consultRequestDto.getPersonalColorId());

        /*기존 ConsultColor 데이터는 삭제*/
        List<ConsultColor> consultColors = consult.getConsultColors();
        consultColors.clear();

        List<ColorResponseDto> colorList = new ArrayList<>();

        for(ColorRequestDto colorRequestDto : consultRequestDto.getColors()) {

            Color color = colorRepository.findById(colorRequestDto.getColorId())
                    .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));

            ConsultColor consultColor = new ConsultColor();
            consultColor.setConsult(consult);
            consultColor.setColor(color);

            consultColors.add(consultColor);

            ColorResponseDto colorResponseDto = ColorResponseDto.builder()
                    .colorId(color.getId())
                    .name(color.getName())
                    .r(color.getR())
                    .g(color.getG())
                    .b(color.getB())
                    .build();

            colorList.add(colorResponseDto);
        }

        consult.setConsultColors(consultColors);
        consultRepository.save(consult);

        return createConsultUserResponseDto(consult, member, colorList);
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
        /*유효성 검사*/
        Consult consult = consultRepository.findByUuid(uuid)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULT_NOT_FOUND_404));

        Member member = memberRepository.findById(consult.getMemberId())
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        return createConsultUserResponseDto(consult, member, null);
    }

    private ConsultUserResponseDto createConsultUserResponseDto (Consult consult, Member member, List<ColorResponseDto> colorList) {

        ConsultUserResponseDto consultUserResponseDto = null;

        if(consult == null) {
            consultUserResponseDto = ConsultUserResponseDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .profileImageUrl(member.getProfileImageUrl())
                    .consultedDate(null)
                    .personalColorId(1)
                    .age(member.getAge())
                    .colors(null)
                    .personalColorTypeId(null)
                    .genderEnum(member.getGender())
                    .consultedContent(null)
                    .consultedDrawing(null)
                    .consultedFile(null)
                    .build();
        } else {
            consultUserResponseDto = ConsultUserResponseDto.builder()
                    .memberId(consult.getMemberId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .consultedDate(consult.getConsultedDate())
                    .profileImageUrl(member.getProfileImageUrl())
                    .personalColorId(consult.getPersonalColorId())
                    .age(member.getAge())
                    .colors(colorList)
                    .personalColorTypeId(consult.getPersonalColorTypeId())
                    .genderEnum(member.getGender())
                    .consultedContent(consult.getConsultedContent())
                    .consultedDrawing(consult.getConsultedDrawing())
                    .consultedFile(consult.getConsultedFile())
                    .uuid(consult.getUuid())
                    .build();
        }

        return consultUserResponseDto;
    }
}
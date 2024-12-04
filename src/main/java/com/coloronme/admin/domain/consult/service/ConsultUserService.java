package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeDto;
import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.entity.ConsultColor;
import com.coloronme.product.color.entity.Color;
import com.coloronme.product.color.repository.ColorRepository;
import com.coloronme.product.member.entity.Member;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.product.personalColor.entity.PersonalColorGroup;
import com.coloronme.product.personalColor.entity.PersonalColorType;
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
public class ConsultUserService {
    private final MemberRepository memberRepository;
    private final ConsultRepository consultRepository;
    private final PersonalColorTypeRepository personalColorTypeRepository;
    private final ColorRepository colorRepository;

    @Transactional
    public ConsultUserResponseDto registerConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        Optional<Consult> consult = consultRepository.findByMemberId(userId);

        /*진단 결과가 있는 경우에는 진단 결과 수정*/
        if (consult.isPresent()) {
            return updateConsultUser(consultantId, userId, consultRequestDto);
        }

        /*진단 결과가 없는 경우에는 새로운 진단 정보 생성*/
        PersonalColorType personalColorType = personalColorTypeRepository.findById(consultRequestDto.getPersonalColorTypeId())
                .orElseThrow(() -> new RequestException(ErrorCode.PERSONAL_COLOR_TYPE_NOT_FOUND_404));
        member.setPersonalColorId(consultRequestDto.getPersonalColorTypeId());

        /*uuid 생성*/
        UUID uuid = UUID.randomUUID();
        consultRequestDto.setUuid(uuid.toString());

        Consult newConsult = new Consult(consultantId, userId, personalColorType, consultRequestDto);

        List<ConsultColor> consultColors = createConsultColors(newConsult, consultRequestDto.getColors());

        newConsult.setConsultColors(consultColors);

        /*consult 등록*/
        consultRepository.save(newConsult);

        return createConsultUserResponseDto(newConsult, member);
    }

    public ConsultUserResponseDto selectConsultUserByUserId(int userId) {
        /*유효성 검사*/
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        Consult consult = consultRepository.findByMemberId(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULT_NOT_FOUND_404));

        return createConsultUserResponseDto(consult, member);
    }

    public List<ConsultUserResponseDto> selectConsultUserList(int consultantId) {

        List<Consult> consultList = consultRepository.findAllByConsultantId(consultantId);

        return consultList.stream()
                .map(consult -> {
                    Member member = memberRepository.findById(consult.getMemberId())
                            .orElseThrow(()-> new RequestException(ErrorCode.USER_NOT_FOUND_404));
                    return createConsultUserResponseDto(consult, member);
                })
                .toList();
    }

    public ConsultUserResponseDto selectConsultUserByUuid(String uuid) {
        /*유효성 검사*/
        Consult consult = consultRepository.findByUuid(uuid)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULT_NOT_FOUND_404));

        Member member = memberRepository.findById(consult.getMemberId())
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        return createConsultUserResponseDto(consult, member);
    }

    @Transactional
    public ConsultUserResponseDto updateConsultUser(int consultantId, int userId, ConsultRequestDto consultRequestDto) {
        /*유효성 검사*/
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        Consult consult = consultRepository.findByMemberId(userId)
                .orElseThrow(() -> new RequestException(ErrorCode.CONSULT_NOT_FOUND_404));

        PersonalColorType personalColorType = personalColorTypeRepository.findById(consultRequestDto.getPersonalColorTypeId())
                        .orElseThrow(() -> new RequestException(ErrorCode.PERSONAL_COLOR_TYPE_NOT_FOUND_404));

        consult.setPersonalColorType(personalColorType);
        consult.setConsultedContent(consultRequestDto.getConsultedContent());
        consult.setConsultedDrawing(consultRequestDto.getConsultedDrawing());
        consult.setConsultedFile(consultRequestDto.getConsultedFile());
        consult.setUpdatedAt(LocalDateTime.now());

        member.setPersonalColorId(consultRequestDto.getPersonalColorTypeId());

        /*기존 ConsultColor 데이터는 삭제*/
        List<ConsultColor> consultColors = createConsultColors(consult, consultRequestDto.getColors());

        consult.getConsultColors().clear();
        consult.getConsultColors().addAll(consultColors);

        consultRepository.save(consult);

        return createConsultUserResponseDto(consult, member);
    }

    public ConsultUserResponseDto verifyUserQr(int consultantId, Member member) {

        Optional<Consult> consultData = consultRepository.findByMemberIdAndConsultantId(consultantId, member.getId());

        if(consultData.isEmpty()) {
            return new ConsultUserResponseDto(
                    member.getId(),
                    member.getNickname(),
                    member.getEmail(),
                    member.getProfileImageUrl(),
                    null,
                    member.getAge(),
                    member.getGender(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }

        Consult consult = consultData.get();
        return createConsultUserResponseDto(consult, member);
    }

    /*진단자 + 진단 정보에 필요한 Response 양식*/
    private ConsultUserResponseDto createConsultUserResponseDto (Consult consult, Member member) {
        PersonalColorType personalColorType = consult.getPersonalColorType();
        PersonalColorGroup personalColorGroup = personalColorType.getPersonalColorGroup();

        List<ColorResponseDto> colorResponseDtoList = consult.getConsultColors().stream()
                .map(consultColor -> {
                    Color color = colorRepository.findById(consultColor.getColor().getId())
                            .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));
                    return new ColorResponseDto(
                            color.getId(),
                            color.getName(),
                            color.getR(),
                            color.getG(),
                            color.getB()
                    );
                })
                .toList();

        PersonalColorTypeDto personalColorTypeDto = new PersonalColorTypeDto(
                personalColorType.getId(),
                personalColorType.getPersonalColorTypeName(),
                personalColorType.isDeleted(),
                colorResponseDtoList
        );

        return new ConsultUserResponseDto(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImageUrl(),
                consult.getConsultedDate(),
                member.getAge(),
                member.getGender(),
                consult.getConsultedContent(),
                consult.getConsultedDrawing(),
                personalColorGroup.getPersonalColorGroupName(),
                personalColorTypeDto,
                consult.getConsultedFile(),
                consult.getUuid()
        );
    }

    private List<ConsultColor> createConsultColors(Consult consult, List<Integer> colorIds) {
        List<ConsultColor> consultColors = new ArrayList<>();
        for (Integer colorId : colorIds) {
            Color color = colorRepository.findById(colorId)
                    .orElseThrow(() -> new RequestException(ErrorCode.COLOR_NOT_FOUND_404));
            consultColors.add(new ConsultColor(color, consult));
        }
        return consultColors;
    }
}
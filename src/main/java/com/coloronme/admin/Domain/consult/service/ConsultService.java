package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.entity.Member;
import com.coloronme.admin.domain.consult.entity.PersonalColor;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
import com.coloronme.admin.domain.consult.repository.PersonalColorRepository;
import com.coloronme.admin.domain.consult.repository.UserRepository;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConsultService {
    private final UserRepository userRepository;
    private final ConsultRepository consultUserRepository;
    private final ConsultantRepository consultantRepository;
    private final PersonalColorRepository personalColorRepository;

    @Transactional
    public void registerConsultUser(String consultantEmail, Long userId, ConsultRequestDto consultRequestDto) {

        Optional<Member> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if (consultant.isEmpty()) {
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        Optional<PersonalColor> personalColor = personalColorRepository.findById(consultRequestDto.getPersonalColorId());
        if (personalColor.isEmpty()) {
            throw new RequestException(ErrorCode.PERSONAL_COLOR_NOT_FOUND_404);
        }

        Member memberData = user.get();
        memberData.setPersonalColorId(consultRequestDto.getPersonalColorId());
        Consult consult = new Consult(consultant.get().getId(), userId, consultRequestDto);

        consultUserRepository.save(consult);
    }
}
package com.coloronme.admin.domain.consult.service;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.entity.User;
import com.coloronme.admin.domain.consult.repository.ConsultRepository;
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

    @Transactional
    public void registerConsultUser(String consultantEmail, Long userId, ConsultRequestDto consultRequestDto) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }

        Optional<Consultant> consultant = consultantRepository.findByEmail(consultantEmail);
        if (consultant.isEmpty()) {
            throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }

        User userData = user.get();
        userData.setPersonalColorId(consultRequestDto.getPersonalColorId());
        Consult consult = new Consult(consultant.get().getId(), userId, consultRequestDto);

        consultUserRepository.save(consult);
    }
}

package com.coloronme.admin.domain.consultant.service;

import com.coloronme.admin.domain.consultant.dto.request.LoginRequestDto;
import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import com.coloronme.admin.domain.consultant.dto.response.LoginResponseDto;
import com.coloronme.admin.domain.consultant.dto.response.SignupResponseDto;
import com.coloronme.admin.domain.consultant.entity.RefreshToken;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.domain.consultant.repository.RefreshTokenRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.admin.global.dto.JwtDto;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.admin.domain.consultant.entity.RoleType;
import com.coloronme.admin.domain.consultant.entity.Consultant;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ConsultantRepository consultantRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseDto<SignupResponseDto> signup(ConsultantRequestDto consultantRequestDto) {

        if (consultantRepository.findByEmail(consultantRequestDto.getEmail()).isPresent()) {
            throw new RequestException(ErrorCode.EMAIL_DUPLICATION_409);
        }
        consultantRequestDto.setEncodedPwd(passwordEncoder.encode(consultantRequestDto.getPassword()));
        Consultant consultant = new Consultant(consultantRequestDto, RoleType.ROLE_CONSULTANT);

        consultantRepository.save(consultant);

        return ResponseDto.success(
                SignupResponseDto.builder()
                        .build()
        );
    }

    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 이메일 있는지 확인
        Consultant consultant = consultantRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new RequestException(ErrorCode.LOGIN_NOT_FOUND_404)
        );
        // 비밀번호 있는지 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), consultant.getPassword())) {
            throw new RequestException(ErrorCode.LOGIN_NOT_FOUND_404);
        }

            // 엑세스토콘, 리프레쉬토큰 생성
            JwtDto jwtDto = jwtUtil.createAllToken(loginRequestDto.getEmail());
//            // 리프레쉬 토큰은 DB에서 찾기
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByConsultantEmail(loginRequestDto.getEmail());
//            // 리프레쉬토큰 null인지 아닌지 에 따라서
//            // 값을 가지고있으면 save
//            // 값이 없으면 newToken 만들어내서 save
            if (refreshToken.isPresent()) {
                refreshTokenRepository.save(refreshToken.get().updateToken(jwtDto.getRefreshToken()));
            } else {
                RefreshToken newToken = new RefreshToken(jwtDto.getRefreshToken(), loginRequestDto.getEmail());
                refreshTokenRepository.save(newToken);
            }
            setHeader(response, jwtDto);

        return ResponseDto.success(
                LoginResponseDto.builder()
                        .email(consultant.getEmail())
                        .roleType(consultant.getRoleType())
                        .build()
        );
    }
    private void setHeader(HttpServletResponse response, JwtDto jwtDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, jwtDto.getRefreshToken());
    }
}



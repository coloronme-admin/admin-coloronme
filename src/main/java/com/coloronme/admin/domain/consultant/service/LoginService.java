package com.coloronme.admin.domain.consultant.service;

import com.coloronme.admin.domain.consultant.dto.response.TokenResponseDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@Service
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

        return ResponseDto.status(
                SignupResponseDto.builder()
                        .email(consultant.getEmail())
                        .colorGroup(consultant.getColorGroup())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Consultant consultant = consultantRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new RequestException(ErrorCode.LOGIN_NOT_FOUND_404)
        );

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), consultant.getPassword())) {
            throw new RequestException(ErrorCode.LOGIN_NOT_FOUND_404);
        }

        // 엑세스토콘, 리프레쉬토큰 생성
        JwtDto tokenDto = jwtUtil.createAllToken(consultant.getId());
        String firstAccessToken = tokenDto.getAccessToken();
        String firstRefreshToken = tokenDto.getRefreshToken().substring(7);
        int consultantId = consultant.getId();
        // 리프레쉬 토큰은 DB에서 찾기
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByConsultantId(consultantId);
        /*기존회원로그인*/
        if (refreshToken.isPresent()) {
            RefreshToken updateToken = refreshToken.get().updateToken(firstRefreshToken);
            refreshTokenRepository.save(updateToken);
        /*신규회원로그인*/
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), consultant.getId());
            refreshTokenRepository.save(newToken);
        }

        return ResponseDto.status(
                LoginResponseDto.builder()
                        .email(consultant.getEmail())
                        .colorGroup(consultant.getColorGroup())
                        .roleType(consultant.getRoleType())
                        .accessToken(firstAccessToken)
                        .refreshToken(tokenDto.getRefreshToken())
                        .build()
        );
    }

    private void setHeader(HttpServletResponse response, String accessToken) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, accessToken);
    }

    @Transactional
    public ResponseDto<TokenResponseDto> reissueToken(String refreshToken, HttpServletResponse response) {
        String validationToken = refreshToken.substring(7);
        if (!jwtUtil.refreshTokenValidation(validationToken)) {
            throw new RequestException(ErrorCode.JWT_BAD_TOKEN_500);
        }
        int consultantId = jwtUtil.getIdFromToken(validationToken);
        String newAccessToken = jwtUtil.createToken(consultantId, "Access");

        return ResponseDto.status(
                TokenResponseDto.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(refreshToken)
                        .build()
        );
    }
}



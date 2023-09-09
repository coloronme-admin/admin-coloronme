package com.coloronme.admin.Domain.service;


import com.coloronme.admin.Domain.dto.request.LoginRequestDto;
import com.coloronme.admin.Domain.dto.request.MemberRequestDto;
import com.coloronme.admin.Domain.dto.response.LoginResponseDto;
import com.coloronme.admin.Domain.dto.response.SignupResponseDto;
import com.coloronme.admin.Domain.entity.RefreshToken;
import com.coloronme.admin.Domain.repository.MemberRepository;
import com.coloronme.admin.Domain.repository.RefreshTokenRepository;
import com.coloronme.admin.Global.exception.ErrorCode;
import com.coloronme.admin.Global.exception.RequestException;
import com.coloronme.admin.Global.dto.JwtDto;
import com.coloronme.admin.Global.dto.ResponseDto;
import com.coloronme.admin.Global.jwt.JwtUtil;
import com.coloronme.admin.Domain.entity.Authority;
import com.coloronme.admin.Domain.entity.Member;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;

@Service
@Component
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public ResponseDto<SignupResponseDto> signup(MemberRequestDto memberRequestDto) {

        if (memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new RequestException(ErrorCode.EMAIL_DUPLICATION_409);
        }
        memberRequestDto.setEncodedPwd(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member member = new Member(memberRequestDto, Authority.ROLE_USER);

        memberRepository.save(member);

        return ResponseDto.success(
                SignupResponseDto.builder()
                        .build()
        );
    }

    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 이메일 있는지 확인
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new RequestException(ErrorCode.LOGIN_NOT_FOUND_404)
        );
        // 비밀번호 있는지 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new RequestException(ErrorCode.LOGIN_NOT_FOUND_404);
        }

            // 엑세스토콘, 리프레쉬토큰 생성
            JwtDto jwtDto = jwtUtil.createAllToken(loginRequestDto.getEmail());

//            // 리프레쉬 토큰은 DB에서 찾기
//            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(loginRequestDto.getEmail());
//
//            // 리프레쉬토큰 null인지 아닌지 에 따라서
//            // 값을 가지고있으면 save
//            // 값이 없으면 newToken 만들어내서 save
//            if (refreshToken.isPresent()) {
//                refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
//            } else {
//                RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getEmail());
//                refreshTokenRepository.save(newToken);
//            }

            setHeader(response, jwtDto);

        return ResponseDto.success(
                LoginResponseDto.builder()
                        .email(member.getEmail())
                        .authority(member.getAuthority())
                        .build()

        );
    }
    private void setHeader(HttpServletResponse response, JwtDto jwtDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, jwtDto.getRefreshToken());
    }

}



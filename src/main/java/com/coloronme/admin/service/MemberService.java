package com.coloronme.admin.service;

import com.coloronme.admin.domain.Member;
import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.dto.request.MemberLoginRequestDto;
import com.coloronme.admin.dto.response.JwtResponseDto;
import com.coloronme.admin.repository.MemberRepository;
import com.coloronme.admin.utils.enums.MemberRoleEnum;
import com.coloronme.admin.utils.exception.ApiException;
import com.coloronme.admin.utils.exception.DuplicateException;
import com.coloronme.admin.utils.exception.ErrorCode;
import com.coloronme.admin.utils.exception.InvalidByEmailException;
import com.coloronme.admin.utils.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            log.error("duplicated member email error.");
            throw new DuplicateException(ErrorCode.DUPLICATED_BY_EMAIL);
        }
        Member member = new Member();
        member.setEmail(memberRequestDto.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()));
        memberRepository.save(member);
        log.info("sign up success.");
    }

    public JwtResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail());
        if(member == null){
            log.error("invalid email error");
            throw new InvalidByEmailException(ErrorCode.INVALID_BY_EMAIL);
        }
        if(!bCryptPasswordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword())){
            log.error("id, password mismatch error.");
            throw new ApiException(ErrorCode.NOT_FOUND_ACCOUNT);
        }

        /*JWT 생성*/
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberLoginRequestDto.getEmail(),
                        memberLoginRequestDto.getPassword())
        );

        String accessToken = jwtProvider.createToken(member.getEmail(), MemberRoleEnum.ADMIN);

    }
}

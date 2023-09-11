package com.coloronme.admin.service;

import com.coloronme.admin.domain.Member;
import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.dto.request.MemberLoginRequestDto;
import com.coloronme.admin.repository.MemberRepository;
import com.coloronme.admin.utils.enums.MemberRoleType;
import com.coloronme.admin.utils.exception.ApiException;
import com.coloronme.admin.utils.exception.DuplicateException;
import com.coloronme.admin.utils.exception.ErrorCode;
import com.coloronme.admin.utils.exception.InvalidByEmailException;
import com.coloronme.admin.utils.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
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
            log.error("email 중복 오류.");
            throw new DuplicateException(ErrorCode.DUPLICATED_BY_EMAIL);
        }
        Member member = new Member();
        member.setEmail(memberRequestDto.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()));
        member.setMemberRoleType(MemberRoleType.ADMIN);
        memberRepository.save(member);
    }

    public String login(MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail());
        
        if(member == null){
            log.error("유효하지 않은 email.");
            throw new InvalidByEmailException(ErrorCode.INVALID_BY_EMAIL);
        }
        
        if(!bCryptPasswordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword())){
            log.error("id, password 불일치.");
            throw new ApiException(ErrorCode.NOT_FOUND_ACCOUNT);
        }
        
        String accessToken = jwtProvider.generateAccessToken(member); /*access token*/
        jwtProvider.generateRefreshToken(member); /*refresh token*/

        return accessToken;
    }

    /*만료된 access token 재발급*/
    public String againGenerateAccessToken(String expiredAccessToken) {
        return jwtProvider.againGenerateAccessToken(expiredAccessToken);
    }
}

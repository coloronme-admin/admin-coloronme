package com.coloronme.admin.service;

import com.coloronme.admin.domain.Member;
import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void signup(MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setEmail(memberRequestDto.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()));
        memberRepository.save(member);
        log.info("sign up success.");
    }


    public int isDuplicateEmail(String email){
        return memberRepository.findByEmail(email);
    }
}

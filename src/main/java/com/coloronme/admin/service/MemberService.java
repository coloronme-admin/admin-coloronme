package com.coloronme.admin.service;

import com.coloronme.admin.domain.Member;
import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public String signup(MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setEmail(memberRequestDto.getEmail());
        member.setPassword(memberRequestDto.getPassword());
        memberRepository.save(member);
        return "sign up success";
    }
}

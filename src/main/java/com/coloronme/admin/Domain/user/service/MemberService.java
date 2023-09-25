package com.coloronme.admin.domain.user.service;

import com.coloronme.admin.domain.user.entity.Member;
import com.coloronme.admin.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member selectUserById(Long userId) {
        Optional<Member> user = memberRepository.findById(userId);
        return user.orElse(null);
    }
}

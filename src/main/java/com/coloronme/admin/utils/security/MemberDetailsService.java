package com.coloronme.admin.utils.security;

import com.coloronme.admin.domain.Member;
import com.coloronme.admin.repository.MemberRepository;
import com.coloronme.admin.utils.exception.ErrorCode;
import com.coloronme.admin.utils.exception.InvalidByEmailException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            throw new InvalidByEmailException(ErrorCode.INVALID_BY_EMAIL);
        }
        return new MemberDetail(member);
    }
}

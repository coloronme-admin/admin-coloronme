package com.coloronme.admin.global.security;

import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ConsultantRepository consultantRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Consultant consultant = consultantRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("Not Found Account")
                );
        return createUserDetails(consultant);
    }

    private UserDetails createUserDetails(Consultant consultant) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("consultant");

        return new User(
                consultant.getEmail(),
                consultant.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}

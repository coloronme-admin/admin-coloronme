package com.coloronme.admin.global.security;

import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ConsultantRepository consultantRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<Consultant> consultant = consultantRepository.findById(Integer.parseInt(id));
        if(consultant.isEmpty()){
           log.error("NOT FOUND ACCOUNT : " + id);
           throw new RequestException(ErrorCode.CONSULTANT_NOT_FOUND_404);
        }
        return createUserDetails(consultant.get());
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

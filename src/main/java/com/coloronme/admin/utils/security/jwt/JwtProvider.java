package com.coloronme.admin.utils.security.jwt;

import com.coloronme.admin.utils.enums.MemberRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String key;
    private final Long tokenValidTime = 240 * 60 * 1000L;

    public String createToken(String email, MemberRoleEnum memberRoleEnum){

    }

}

package com.coloronme.admin.utils.security.jwt;

import com.coloronme.admin.repository.MemberRepository;
import com.coloronme.admin.utils.enums.MemberRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String key;
    @Value("${jwt.cookie.name}")
    private String jwtCookie;
    private MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*ACCESS 토큰 생성*/
    public String generateAccessToken(String email, MemberRoleEnum memberRoleEnum){
        /*PayLoad에 들어갈 claim 데이터 : 주고 받을 때 필요한 정보를 넣음*/
        long tokenValidTime = 240 * 60 * 1000L;

        Claims claims = Jwts.claims()
                .setSubject("access_token")
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(tokenValidTime));
        claims.put("role", memberRoleEnum);

        /*토큰 생성*/
        String accessToken = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return accessToken;
    }

    /*REFRESH 토큰 생성*/
    public void generateRefreshToken(String email, MemberRoleEnum memberRoleEnum) {

        Claims claims = Jwts.claims()
                .setSubject("refresh_token")
                .setSubject(email)
                .setIssuedAt(new Date());
        claims.put("role", memberRoleEnum);

        String refreshToken = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        
        /*한번 더 해싱*/
        String hashingRefreshToken = bCryptPasswordEncoder.encode(refreshToken);

        /*refresh token Member DB 저장*/
        memberRepository.updateMemberRefreshToken(email, hashingRefreshToken);
    }
        

    /*토큰 검증
    요청에 JWT가 있는지
    유효시간이 지나지 않았는지*/
    public boolean checkJwtToken(HttpServletRequest httpRequest) {



        return false;
    }
    
    
    
    /*토큰 재발급*/
    public String againGenerateAccessToken(HttpServletRequest httpRequest) {


        return null;
    }





}

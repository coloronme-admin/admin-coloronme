package com.coloronme.admin.global.security.jwt;

import com.coloronme.admin.domain.entity.Member;
import com.coloronme.admin.domain.repository.MemberRepository;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.InvalidByEmailException;
import com.coloronme.admin.global.exception.InvalidJwtException;
import com.coloronme.admin.global.security.MemberDetailsService;
import io.jsonwebtoken.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.token.key}")
    private String key;
    private final MemberRepository memberRepository;
    private final MemberDetailsService memberDetailsService;
    final long ACCESS_TOKEN_EXPIRED_TIME = 240 * 60 * 1000L;
    final long REFRESH_TOKEN_EXPIRED_TIME = 1440 * 60 * 1000L;

    /*ACCESS 토큰 생성*/
    public String generateAccessToken(Member member){
        return generateToken(member, ACCESS_TOKEN_EXPIRED_TIME);
    }

    @Transactional
    /*REFRESH 토큰 생성*/
    public void generateRefreshToken(Member member) {
        String refreshToken = generateToken(member, REFRESH_TOKEN_EXPIRED_TIME);
        /*refresh token Member DB 저장*/
        Member updateMember = memberRepository.findByEmail(member.getEmail());
        updateMember.setRefreshToken(refreshToken);
    }

    /*토큰 검증
    - 토큰 안에 claims 정보 확인*/
    public boolean checkJwtToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException e) {
            log.error("토큰 시간 만료.");
            throw new InvalidJwtException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
    }

    /*사용자 정보 반환*/
    public Authentication getAuthentication(String accessToken){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
        if(claims.get("role") == null) {
            log.error("토큰 사용자 권한 오류.");
            throw new InvalidJwtException(ErrorCode.AUTHENTICATION_FAILED);
        }
        UserDetails userDetails = memberDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(memberDetailsService, "", userDetails.getAuthorities());
    }

    /*토큰 재발급*/
    public String againGenerateAccessToken(String expiredAccessToken) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(expiredAccessToken).getBody();
        Member member = memberRepository.findByEmail(claims.getSubject());
        /*refresh 만료 유무 확인*/
        try{
            if(checkJwtToken(member.getRefreshToken())){
                String againAccessToken = generateToken(member, ACCESS_TOKEN_EXPIRED_TIME);
                member.setRefreshToken(againAccessToken);
                return againAccessToken;
            }
        } catch (ExpiredJwtException e) {
            log.error("유효하지 않은 REFRESH 토큰.");
            throw new InvalidByEmailException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
        return null;
    }

    /*토큰 생성*/
    public String generateToken(Member member, long expiredTime) {
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime));
        claims.put("role", member.getMemberRoleType());

        /*토큰 생성*/
        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}

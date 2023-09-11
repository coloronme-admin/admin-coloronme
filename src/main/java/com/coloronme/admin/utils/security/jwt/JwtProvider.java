package com.coloronme.admin.utils.security.jwt;

import com.coloronme.admin.domain.Member;
import com.coloronme.admin.repository.MemberRepository;
import com.coloronme.admin.utils.enums.MemberRoleType;
import com.coloronme.admin.utils.exception.ErrorCode;
import com.coloronme.admin.utils.exception.InvalidJwtException;
import com.coloronme.admin.utils.security.MemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.token.key}")
    private String key;
    private final MemberRepository memberRepository;
    private final MemberDetailsService memberDetailsService;
    final long ACCESS_TOKEN_EXPIRED_TIME = 60L;
    final long REFRESH_TOKEN_EXPIRED_TIME = 60L;

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
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return true;
        } catch (InvalidJwtException e) {
            log.error("토큰 시간 만료.");
            throw new InvalidJwtException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
    }

    /*해당 토큰이 유효한 key 값을 갖고 있는지 확인*/
    private Claims parseClaims(String jwtToken) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
        } catch (InvalidJwtException e) {
            log.error("토큰 시간 만료.");
            throw new InvalidJwtException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
    }

    /*사용자 정보 반환*/
    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);
        if(claims.get("role") == null) {
            log.error("토큰 사용자 권한 오류.");
            throw new InvalidJwtException(ErrorCode.NOT_PERMISSION_ROLE_TOKEN);
        }
        UserDetails userDetails = memberDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(memberDetailsService, "", userDetails.getAuthorities());
    }

    /*토큰 재발급*/
    public String againGenerateAccessToken(String expiredAccessToken) {
        Claims claims = parseClaims(expiredAccessToken);
        Member member = memberRepository.findByEmail(claims.getSubject());
        /*refresh 만료 유무 확인*/
        if(checkJwtToken(member.getRefreshToken())){
            System.out.println("재발급????" + checkJwtToken(member.getRefreshToken()));
            long tokenValidTime = 240 * 60 * 1000L;
            return generateToken(member, tokenValidTime);
        }
        return null;
    }

    /*request로부터 token 추출*/
    String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /*토큰 생성*/
    public String generateToken(Member member, long expiredTime) {
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiredTime));
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

package com.coloronme.admin.global.jwt;

import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.consultant.entity.RefreshToken;
import com.coloronme.admin.domain.consultant.repository.ConsultantRepository;
import com.coloronme.admin.domain.consultant.repository.RefreshTokenRepository;
import com.coloronme.admin.global.dto.JwtDto;
import com.coloronme.admin.domain.consultant.entity.RoleType;
import com.coloronme.admin.global.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ConsultantRepository consultantRepository;

    private static final String AUTHORITIES_KEY = "auth";
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private static final long ACCESS_TIME = 1000 * 60 * 60 * 24;
    private static final long REFRESH_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final String BEARER_TYPE = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String getHeaderToken(HttpServletRequest request, String type) {
        String bearerToken = type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public JwtDto createAllToken(int id) {
        return new JwtDto(createToken(id, "Access"), createToken(id, "Refresh"));
    }

    public String createToken(int id, String type) {
        Date date = new Date();

        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return BEARER_TYPE + Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim(AUTHORITIES_KEY, RoleType.ROLE_CONSULTANT.toString())
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public Boolean tokenValidation(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Boolean refreshTokenValidation(String token) {
        /*1차 토큰 검증*/
        if (!tokenValidation(token)){
            return false;}
        /*DB에 저장한 토큰 비교*/
        int consultantId = getIdFromToken(token);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByConsultantId(consultantId);
        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());


    }

    /*인증 객체 생성*/
    public Authentication createAuthentication(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /*토큰에서 id 가져오는 기능*/
    public int getIdFromToken(String token) {
        return Integer.parseInt(Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
    /*request에서 token에 담긴 id 정보 갖고오는 기능*/
    public int getIdFromRequest(HttpServletRequest request, String type) {
        String accessToken = getHeaderToken(request, type);
        return getIdFromToken(accessToken);
    }

    /*access token 에서 추출한 사용자 정보가 유효한 정보인지 확인하는 기능*/
    public int checkValidDataByJwt(int consultantId) {
        Optional<Consultant> consultant = consultantRepository.findById(consultantId);
        if(consultant.isEmpty()){
            return 0;
        }
        return 1;
    }

    private Claims parseClaims(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}

package com.coloronme.admin.utils.security.jwt;

import com.coloronme.admin.utils.exception.ErrorCode;
import com.coloronme.admin.utils.exception.InvalidJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*요청 헤더로부터 access token 추출*/
        String accessToken = jwtProvider.resolveToken(request);
        try {
            /*access token 유효성 검사*/
            if (accessToken != null && jwtProvider.checkJwtToken(accessToken)){
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidJwtException e) {
            SecurityContextHolder.clearContext();
            response.sendError(ErrorCode.EXPIRED_JWT_TOKEN.getCode(), ErrorCode.EXPIRED_JWT_TOKEN.getMessage());
        }
        /*처리 후 다음 필터로 이동*/
        filterChain.doFilter(request, response);
    }
}

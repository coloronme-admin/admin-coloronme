package com.coloronme.admin.global.jwt;


import com.coloronme.admin.global.dto.GlobalResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("doFilterInternal 여기를 먼저 거치나?");

        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if (accessToken != null) {
            if (!jwtUtil.tokenValidation(accessToken)) {
                jwtExceptionHandler(response, "AccessToken Expired", HttpStatus.BAD_REQUEST);
                return;
            }
            setAuthentication(jwtUtil.getEmailFromToken(accessToken));
        } else if (refreshToken != null) {
            if (!jwtUtil.refreshTokenValidation(refreshToken)) {
                jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST);
                return;
            }
            setAuthentication(jwtUtil.getEmailFromToken(refreshToken));
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        /*context에 담아두면 UsernamePasswordAuthenticationFilter에서 인증된걸 알게 해준다*/
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalResDto(msg, status.value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

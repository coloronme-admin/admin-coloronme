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
        log.info("JwtAuthFilter 접속 --------------------------------");
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        /*요청에 access token이 없는 경우*/
        if(accessToken==null){
            log.error("Request does not have Access Token.");
        }

        if (accessToken != null) {
            /*access token의 유효기간이 만료된 경우*/
            if (!jwtUtil.tokenValidation(accessToken)) {
                jwtExceptionHandler(response, "AccessToken Expired", HttpStatus.BAD_REQUEST);
                return;
            }
            /*access token에 들어있는 사용자 정보가 유효하지 않는 경우(DB에 없는 경우)*/
            int consultantId = jwtUtil.getIdFromToken(accessToken);
            if(jwtUtil.checkValidDataByJwt(consultantId)==0){
                jwtExceptionHandler(response, "Request does not have access token.", HttpStatus.BAD_REQUEST);
                return;
            }
            setAuthentication(String.valueOf(jwtUtil.getIdFromToken(accessToken)));
        } else if (refreshToken != null) {
            if (!jwtUtil.refreshTokenValidation(refreshToken)) {
                jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String id) {
        Authentication authentication = jwtUtil.createAuthentication(id);
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

package com.coloronme.admin.global.jwt;

import com.coloronme.admin.global.dto.GlobalResDto;
import com.fasterxml.jackson.core.JsonGenerator;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String url = request.getRequestURI();
        /*요청에 access token이 없는 경우*/
        if(!((url.startsWith("/v3/api-docs") || url.startsWith("/swagger-ui") || url.equals("/health") || url.startsWith("/color/group")
                || url.equals("/login") || url.equals("/signup") || url.equals("/members/**") || url.equals("/refresh-token")))){
            if(accessToken == null) {
                jwtExceptionHandler(response, "Access Token이 존재하지 않습니다.", HttpStatus.FORBIDDEN);
                return;
            } else {
                /*access token의 유효기간이 만료된 경우*/
                if (!jwtUtil.tokenValidation(accessToken)) {
                    jwtExceptionHandler(response, "Access Token이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
                    return;
                }
                /*access token에 들어있는 사용자 정보가 유효하지 않는 경우(DB에 없는 경우)*/
                int consultantId = jwtUtil.getIdFromToken(accessToken);
                if(jwtUtil.checkValidDataByJwt(consultantId)==0){
                    jwtExceptionHandler(response, "접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
                    return;
                }
                setAuthentication(String.valueOf(jwtUtil.getIdFromToken(accessToken)));
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String id) {
        Authentication authentication = jwtUtil.createAuthentication(id);
        /*context에 담아두면 UsernamePasswordAuthenticationFilter에서 인증된걸 알게 해준다*/
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String message, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            String json = objectMapper.writeValueAsString(new GlobalResDto("jwt error", message));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

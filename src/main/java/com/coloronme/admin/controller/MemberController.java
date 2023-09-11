package com.coloronme.admin.controller;

import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.dto.request.MemberLoginRequestDto;
import com.coloronme.admin.service.MemberService;
import com.coloronme.admin.utils.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/signup")
    public ResponseEntity<MemberRequestDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        memberService.signup(memberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        String accessToken = memberService.login(memberLoginRequestDto);
        if(accessToken != null) {
            ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                    .maxAge(10)
                    .path("/")
                    .secure(true)
                    .httpOnly(true)
                    .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("token is valid.");
        }
        return ResponseEntity.badRequest().body("token is invalid.");
    }

    @PostMapping(value="/refresh")
    public ResponseEntity<?> againGenerateAccessToken(HttpServletRequest request) {
        String expiredAccessToken = jwtProvider.resolveToken(request);
        String refreshAccessToken = memberService.againGenerateAccessToken(expiredAccessToken);
        if(refreshAccessToken != null) {
            ResponseCookie cookie = ResponseCookie.from("access_token", refreshAccessToken)
                    .maxAge(240 * 50 * 1000L)
                    .path("/")
                    .secure(true)
                    .httpOnly(true)
                    .build();

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("token is refreshed.");
        }
        return ResponseEntity.badRequest().body("token is refreshed.");
    }
}

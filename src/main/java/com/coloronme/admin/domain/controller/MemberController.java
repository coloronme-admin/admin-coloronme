package com.coloronme.admin.domain.controller;

import com.coloronme.admin.domain.dto.request.MemberRequestDto;
import com.coloronme.admin.domain.dto.request.MemberLoginRequestDto;
import com.coloronme.admin.domain.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/signup")
    public ResponseEntity<MemberRequestDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        memberService.signup(memberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        String accessToken = memberService.login(memberLoginRequestDto);
        if(accessToken != null) {
           ResponseCookie jwtCookie = generateJwtCookie(accessToken);
           return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body("token is valid.");
        }
        return ResponseEntity.badRequest().body("token is invalid.");
    }

    @PostMapping(value="/refresh")
    public ResponseEntity<?> againGenerateAccessToken(@CookieValue(name="access_token") String expiredAccessToken) {
        String refreshAccessToken = memberService.againGenerateAccessToken(expiredAccessToken);
        if(refreshAccessToken != null) {
            ResponseCookie jwtCookie = generateJwtCookie(refreshAccessToken);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body("token is refreshed.");
        }
        return ResponseEntity.badRequest().body("token is refreshed.");
    }

    /*JWT가 담긴 쿠키 생성*/
    private ResponseCookie generateJwtCookie(String accessToken) {
        return ResponseCookie.from("access_token", accessToken)
                    .maxAge(3600)
                    .path("/")
                    .secure(true)
                    .httpOnly(true)
                    .build();
    }

}

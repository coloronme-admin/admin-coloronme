package com.coloronme.admin.controller;

import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.dto.request.MemberLoginRequestDto;
import com.coloronme.admin.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class MemberController {

    private final MemberService memberService;

    /*회원가입*/
    @PostMapping(value = "/signup")
    public ResponseEntity<MemberRequestDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        memberService.signup(memberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        String accessToken = memberService.login(memberLoginRequestDto);
        if(accessToken != null) {
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessToken)
                    .body("token is valid.");
        }
        return ResponseEntity.badRequest().body("token is invalid");
    }
}

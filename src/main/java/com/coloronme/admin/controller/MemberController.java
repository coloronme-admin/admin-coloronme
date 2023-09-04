package com.coloronme.admin.controller;

import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.dto.request.MemberLoginRequestDto;
import com.coloronme.admin.dto.response.JwtResponseDto;
import com.coloronme.admin.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*회원가입*/
    @PostMapping(value = "/signup")
    public ResponseEntity<MemberRequestDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        memberService.signup(memberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value="/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        JwtResponseDto jwtResponseDto = memberService.login(memberLoginRequestDto);
        return ResponseEntity.status(OK).body(jwtResponseDto);
    }
}

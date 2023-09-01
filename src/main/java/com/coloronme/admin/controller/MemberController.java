package com.coloronme.admin.controller;

import com.coloronme.admin.dto.request.MemberRequestDto;
import com.coloronme.admin.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.coloronme.admin.Domain.member.controller;

import com.coloronme.admin.Domain.member.dto.request.MemberRequestDto;
import com.coloronme.admin.Domain.member.dto.request.LoginRequestDto;
import com.coloronme.admin.Domain.member.dto.response.LoginResponseDto;
import com.coloronme.admin.Global.dto.ResponseDto;
import com.coloronme.admin.Domain.member.dto.response.SignupResponseDto;
import com.coloronme.admin.Domain.member.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;


    @PostMapping("/signup")
    public ResponseDto<SignupResponseDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return loginService.signup(memberRequestDto);
    }
    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto,HttpServletResponse response
                                               ) {
        return loginService.login(loginRequestDto, response);
    }
}

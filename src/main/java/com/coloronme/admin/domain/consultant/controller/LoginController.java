package com.coloronme.admin.domain.consultant.controller;

import com.coloronme.admin.domain.consultant.dto.request.LoginRequestDto;
import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import com.coloronme.admin.domain.consultant.dto.response.LoginResponseDto;
import com.coloronme.admin.domain.consultant.dto.response.SignupResponseDto;
import com.coloronme.admin.domain.consultant.service.LoginService;
import com.coloronme.admin.global.dto.ResponseDto;
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
    public ResponseDto<SignupResponseDto> signup(@RequestBody @Valid ConsultantRequestDto consultantRequestDto) {
        return loginService.signup(consultantRequestDto);
    }
    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response
                                               ) {
        return loginService.login(loginRequestDto, response);
    }
}

package com.coloronme.admin.domain.consultant.controller;

import com.coloronme.admin.domain.consultant.dto.TokenRequestDto;
import com.coloronme.admin.domain.consultant.dto.TokenResponseDto;
import com.coloronme.admin.domain.consultant.dto.request.LoginRequestDto;
import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import com.coloronme.admin.domain.consultant.dto.response.LoginResponseDto;
import com.coloronme.admin.domain.consultant.dto.response.SignupResponseDto;
import com.coloronme.admin.domain.consultant.service.LoginService;
import com.coloronme.admin.global.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Login Controller" , description = "회원가입/로그인 컨트롤러")
public class LoginController {
    private final LoginService loginService;
//    private final TokenService tokenService;


    @Operation(summary = "signup", description = "회원가입")
    @PostMapping("/signup")
    public ResponseDto<SignupResponseDto> signup(@RequestBody @Valid ConsultantRequestDto consultantRequestDto) {
        return loginService.signup(consultantRequestDto);
    }

    @Operation(summary = "login", description = "로그인")
    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response
    )
    {
        return loginService.login(loginRequestDto, response);
    }

    @PostMapping("/refresh-token")
    public ResponseDto<TokenResponseDto> reissueToken(@RequestBody Map<String, String> refreshToken, HttpServletRequest request, HttpServletResponse response){
        String token = refreshToken.get("refreshToken");
        return loginService.reissueToken(token, response);
    }
}


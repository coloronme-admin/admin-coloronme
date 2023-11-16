package com.coloronme.admin.domain.consultant.controller;

import com.coloronme.admin.domain.consultant.dto.request.LoginRequestDto;
import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import com.coloronme.admin.domain.consultant.dto.response.LoginResponseDto;
import com.coloronme.admin.domain.consultant.dto.response.SignupResponseDto;
import com.coloronme.admin.domain.consultant.service.LoginService;
import com.coloronme.admin.global.dto.ResponseDto;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
//@Api(tags = "coloronme admin API")
public class LoginController {
    private final LoginService loginService;
    @PostMapping("/signup")
//    @ApiOperation(value = "회원가입", notes = "회원가입 API")
//    @ApiImplicitParam(name = "consultantRequestDto", value = "회원가입시 입력된 정보") //swagger에 사용하는 파라미터에 대한 설명
    public ResponseDto<SignupResponseDto> signup(@RequestBody @Valid ConsultantRequestDto consultantRequestDto) {
        return loginService.signup(consultantRequestDto);
    }
    @PostMapping("/login")
//    @ApiOperation(value = "로그인", notes = "로그인 API")
    public ResponseDto<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response
                                               ) {
        return loginService.login(loginRequestDto, response);
    }
}

package com.coloronme.admin.domain.consult.controller;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultService;
import com.coloronme.admin.domain.user.dto.response.UserResponseDto;
import com.coloronme.admin.domain.user.service.MemberService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class ConsultController {
    private final ConsultService consultService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{userId}")
    public ResponseDto<String> registerConsultUser(HttpServletRequest request, @PathVariable Long userId,
                                                               @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        /*진단자 계정 검증*/
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String consultantEmail = jwtUtil.getEmailFromToken(accessToken);
        /*고객 진단 정보 추가*/
        consultService.registerConsultUser(consultantEmail, userId, consultRequestDto);
        return ResponseDto.success(
               "Customer Information Registration Successful.");
    }

    @GetMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> selectConsultUserByUserId(@PathVariable Long userId) {
        ConsultUserResponseDto consultUserResponseDto = consultService.selectConsultUserByUserId(userId);
        return ResponseDto.success(consultUserResponseDto);
    }
}

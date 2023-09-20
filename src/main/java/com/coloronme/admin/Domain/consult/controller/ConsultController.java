package com.coloronme.admin.domain.consult.controller;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultService;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/{userId}")
    public ResponseDto<ConsultResponseDto> registerConsultUser(HttpServletRequest request,
                                                               @RequestParam Long userId,
                                                               @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        String consultantEmail = jwtUtil.getEmailFromToken(request.getHeader(JwtUtil.ACCESS_TOKEN));
        consultService.registerConsultUser(consultantEmail, userId, consultRequestDto);
        return ResponseDto.success(
                ConsultResponseDto.builder()
                        .status("success")
                        .message("Customer registration complete")
                        .build());
    }
}

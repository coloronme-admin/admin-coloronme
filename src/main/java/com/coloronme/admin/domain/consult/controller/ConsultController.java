package com.coloronme.admin.domain.consult.controller;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.member.repository.UserAuthDetailRepository;
import com.coloronme.product.member.service.UserAuthDetailService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
@Slf4j
public class ConsultController {
    private final ConsultService consultService;
    private final UserAuthDetailService userAuthDetailService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "고객 상담 등록", description = "고객 상담 등록")
    @Transactional
    @PostMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> registerConsultUser(HttpServletRequest request, @PathVariable int userId,
                                                               @Valid @RequestBody ConsultRequestDto consultRequestDto) throws IOException {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        ConsultUserResponseDto consultUserResponseDto = consultService.registerConsultUser(consultantId, userId, consultRequestDto);
        return ResponseDto.status(consultUserResponseDto);
    }

    @Operation(summary = "사용자 ID로 고객 상담 조회", description = "사용자 ID로 고객 상담 조회")
    @GetMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> selectConsultUserByUserId(HttpServletRequest request,
                                                                         @PathVariable int userId) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        ConsultUserResponseDto consultUserResponseDto = consultService.selectConsultUserByUserId(userId, consultantId);
        return ResponseDto.status(consultUserResponseDto);
    }

    @Operation(summary = "UUID로 고객 상담 조회", description = "UUID로 고객 상담 조회")
    @GetMapping("/uuid/{uuid}")
    public ResponseDto<ConsultUserResponseDto> selectConsultUserByUserId(HttpServletRequest request,
                                                                         @PathVariable String uuid) {
        ConsultUserResponseDto consultUserResponseDto = consultService.selectConsultUserByUuid(uuid);
        return ResponseDto.status(consultUserResponseDto);
    }

    @Operation(summary = "고객들 상담 조회", description = "고객들 상담 조회")
    @GetMapping()
    public ResponseDto<List<ConsultUserResponseDto>> selectConsultUserList(HttpServletRequest request) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        List<ConsultUserResponseDto> consultUserList = consultService.selectConsultUserList(consultantId);
        return ResponseDto.status(consultUserList);
    }

    @Operation(summary = "고객 상담 수정", description = "고객 상담 수정")
    @Transactional
    @PatchMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> updateConsultUser(HttpServletRequest request, @PathVariable int userId,
                                                 @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        ConsultUserResponseDto consultUserResponseDto = consultService.updateConsultUser(consultantId, userId, consultRequestDto);
        return ResponseDto.status(consultUserResponseDto);
    }

    @Operation(summary = "QR 고객 확인", description = "QR 고객 확인")
    @GetMapping("/qr/{uuid}")
    public ResponseDto<ConsultUserResponseDto> verifyUserQr(HttpServletRequest request, @PathVariable String uuid){
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        Member member = userAuthDetailService.verifyUserQr(uuid);
        ConsultUserResponseDto consultUserResponseDto = consultService.verifyUserQr(consultantId, member);
        return ResponseDto.status(consultUserResponseDto);
    }
}

package com.coloronme.admin.domain.consult.controller;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.member.repository.UserAuthDetailRepository;
import com.coloronme.product.member.service.UserAuthDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class ConsultController {
    private final ConsultService consultService;
    private final UserAuthDetailService userAuthDetailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> registerConsultUser(HttpServletRequest request, @PathVariable int userId,
                                                               @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        ConsultUserResponseDto consultUserResponseDto = consultService.registerConsultUser(consultantId, userId, consultRequestDto);
        return ResponseDto.status(consultUserResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> selectConsultUserByUserId(HttpServletRequest request,
                                                                         @PathVariable int userId) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        ConsultUserResponseDto consultUserResponseDto = consultService.selectConsultUserByUserId(userId, consultantId);
        return ResponseDto.status(consultUserResponseDto);
    }

    @GetMapping()
    public ResponseDto<List<ConsultUserResponseDto>> selectConsultUserList(HttpServletRequest request) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        List<ConsultUserResponseDto> consultUserList = consultService.selectConsultUserList(consultantId);
        return ResponseDto.status(consultUserList);
    }

    @PatchMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> updateConsultUser(HttpServletRequest request, @PathVariable int userId,
                                                 @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        ConsultUserResponseDto consultUserResponseDto = consultService.updateConsultUser(consultantId, userId, consultRequestDto);
        return ResponseDto.status(consultUserResponseDto);
       
    }

    @GetMapping("/qr/{uuid}")
    public ResponseDto<ConsultUserResponseDto> verifyUserQr(HttpServletRequest request, @PathVariable String uuid){
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        Member member = userAuthDetailService.verifyUserQr(uuid);
        ConsultUserResponseDto consultUserResponseDto = consultService.verifyUserQr(consultantId, member);
        return ResponseDto.status(consultUserResponseDto);
    }

}

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
    public ResponseDto<String> registerConsultUser(HttpServletRequest request, @PathVariable int userId,
                                                               @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        consultService.registerConsultUser(consultantId, userId, consultRequestDto);
        return ResponseDto.success(
               "Consult Registration Successful.");
    }

    @GetMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> selectConsultUserByUserId(@AuthenticationPrincipal UserDetails userDetails,
                                                                         @PathVariable int userId) {
        int consultantId = Integer.parseInt(userDetails.getUsername());
        ConsultUserResponseDto consultUserResponseDto = consultService.selectConsultUserByUserId(userId, consultantId);
        return ResponseDto.success(consultUserResponseDto);
    }

    @GetMapping()
    public ResponseDto<List<ConsultUserResponseDto>> selectConsultUserList(HttpServletRequest request) {
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        List<ConsultUserResponseDto> consultUserList = consultService.selectConsultUserList(consultantId);
        return ResponseDto.success(consultUserList);
    }

    @PatchMapping("/{userId}")
    public ResponseDto<String> updateConsultUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int userId,
                                                 @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        String consultantId = userDetails.getUsername();
        consultService.updateConsultUser(consultantId, userId, consultRequestDto);
        return ResponseDto.success("update consult success!");
    }

    @GetMapping("/qr/{uuid}")
    public ResponseDto<ConsultUserResponseDto> verifyUserQr(HttpServletRequest request, @PathVariable String uuid){
        int consultantId = jwtUtil.getIdFromRequest(request, "Access");
        Member member = userAuthDetailService.verifyUserQr(uuid);
        ConsultUserResponseDto consultUserResponseDto = consultService.verifyUserQr(consultantId, member);
        return ResponseDto.success(consultUserResponseDto);
    }

}

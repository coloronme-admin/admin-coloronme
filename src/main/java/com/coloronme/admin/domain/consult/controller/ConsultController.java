package com.coloronme.admin.domain.consult.controller;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultUserResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.admin.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class ConsultController {
    private final ConsultService consultService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{userId}")
    public ResponseDto<String> registerConsultUser(HttpServletRequest request, @PathVariable int userId,
                                                               @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        String consultantEmail = jwtUtil.getEmailFromRequest(request, "Access");
        consultService.registerConsultUser(consultantEmail, userId, consultRequestDto);
        return ResponseDto.success(
               "Consult Registration Successful.");
    }

    @GetMapping("/{userId}")
    public ResponseDto<ConsultUserResponseDto> selectConsultUserByUserId(@AuthenticationPrincipal UserDetails userDetails,
                                                                         @PathVariable int userId) {
        String consultantEmail = userDetails.getUsername();
        ConsultUserResponseDto consultUserResponseDto = consultService.selectConsultUserByUserId(userId, consultantEmail);
        return ResponseDto.success(consultUserResponseDto);
    }

    @GetMapping()
    public ResponseDto<List<ConsultUserResponseDto>> selectConsultUserList(HttpServletRequest request) {
        String consultantEmail = jwtUtil.getEmailFromRequest(request, "Access");
        List<ConsultUserResponseDto> consultUserList = consultService.selectConsultUserList(consultantEmail);
        return ResponseDto.success(consultUserList);
    }

    @PatchMapping("/{userId}")
    public ResponseDto<String> updateConsultUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int userId,
                                                 @Valid @RequestBody ConsultRequestDto consultRequestDto) {
        String consultantEmail = userDetails.getUsername();
        consultService.updateConsultUser(consultantEmail, userId, consultRequestDto);
        return ResponseDto.success("update consult success!");
    }

}

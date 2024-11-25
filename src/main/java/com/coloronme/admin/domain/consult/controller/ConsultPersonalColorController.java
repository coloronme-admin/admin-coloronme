package com.coloronme.admin.domain.consult.controller;


import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultPersonalColorResponseDto;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeDto;
import com.coloronme.admin.domain.consult.service.ConsultPersonalColorService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/color")
public class ConsultPersonalColorController {

    private final JwtUtil jwtUtil;
    private final ConsultPersonalColorService consultPersonalColorService;

    @GetMapping
    public ResponseDto<ConsultPersonalColorResponseDto> getPersonalColorType(HttpServletRequest httpServletRequest) {
        System.out.println("getPersonalColorType controller");
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        ConsultPersonalColorResponseDto consultPersonalColorResponseDto = consultPersonalColorService.getPersonalColorType(consultantId);
        return ResponseDto.status(consultPersonalColorResponseDto);
    }

    @PostMapping
    public ResponseDto<PersonalColorTypeResponseDto> registerPersonalColorType(HttpServletRequest httpServletRequest,
                                                                             @Valid @RequestBody PersonalColorTypeRequestDto personalColorTypeRequestDto){
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        PersonalColorTypeResponseDto personalColorResponseDto = consultPersonalColorService
                .registerPersonalColorType(consultantId, personalColorTypeRequestDto);
        return ResponseDto.status(personalColorResponseDto);
    }
}

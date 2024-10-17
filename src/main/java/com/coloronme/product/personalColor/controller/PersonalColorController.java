package com.coloronme.product.personalColor.controller;

import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.product.personalColor.dto.response.PersonalColorResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorTypeDto;
import com.coloronme.product.personalColor.service.PersonalColorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/color")
@RestController
public class PersonalColorController {

    private final JwtUtil jwtUtil;
    private final PersonalColorService personalColorService;

    /*색상군 조회*/
    @GetMapping("/group")
    public ResponseDto<PersonalColorResponseDto> getColorGroupList(HttpServletRequest httpServletRequest){
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        PersonalColorResponseDto personalColorList = personalColorService.getColorGroupList(consultantId);
        return ResponseDto.status(personalColorList);
    }
}

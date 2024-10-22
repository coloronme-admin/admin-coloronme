package com.coloronme.product.personalColor.controller;

import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.product.personalColor.dto.request.PersonalColorRequestDto;
import com.coloronme.product.personalColor.dto.response.ColorGroupResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorResponseDto;
import com.coloronme.product.personalColor.dto.response.PersonalColorTypeDto;
import com.coloronme.product.personalColor.service.PersonalColorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping(value = "/color")
@RestController
public class PersonalColorController {

    private final JwtUtil jwtUtil;
    private final PersonalColorService personalColorService;

    /*색상군 조회*/
    @GetMapping("/group")
    public ResponseDto<?> getColorGroupList(HttpServletRequest httpServletRequest,
                                                  @Valid PersonalColorRequestDto personalColorRequestDto){
        if(personalColorRequestDto.getGroup() == null) {
            ColorGroupResponseDto colorGroupResponseDto = personalColorService.getColorGroupListByType(personalColorRequestDto);
            return ResponseDto.status(colorGroupResponseDto);
        } else {
            PersonalColorResponseDto personalColorResponseDto = personalColorService.getColorGroupListByTypeAndGroup(personalColorRequestDto);
            return ResponseDto.status(personalColorResponseDto);
        }
    }
}

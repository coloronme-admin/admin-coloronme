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
    /*
    * 쿼리 파라미터로 group이 있느냐 없느냐에 따라 응답 양식이 달라 ?(와일드카드)를 사용하였음
    * 후에 쿼리 파라미터 종류들이 늘어나거나 퍼스널 컬러 색상군 조회 관련하여
    * 여러 요구사항이 늘어나면 응답 객체에 대한 인터페이스를 사용하는 것이 더 좋을 것 같음
    * */
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

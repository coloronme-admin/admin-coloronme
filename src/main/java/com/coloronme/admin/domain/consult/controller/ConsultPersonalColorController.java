package com.coloronme.admin.domain.consult.controller;


import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeRequestDto;
import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeUpdateRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultPersonalColorResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultPersonalColorService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/color")
public class ConsultPersonalColorController {

    private final JwtUtil jwtUtil;
    private final ConsultPersonalColorService consultPersonalColorService;

    @GetMapping
    public ResponseDto<ConsultPersonalColorResponseDto> getPersonalColorType(HttpServletRequest httpServletRequest) {
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

    @PatchMapping("/{personalColorTypeId}")
    public ResponseDto<PersonalColorTypeResponseDto> updatePersonalColorType(HttpServletRequest httpServletRequest,
                                                                             @PathVariable int personalColorTypeId,
                                                                             @RequestBody PersonalColorTypeUpdateRequestDto personalColorTypeUpdateRequestDto) {
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        PersonalColorTypeResponseDto personalColorTypeResponseDto = consultPersonalColorService
                .updatePersonalColorType(consultantId, personalColorTypeId, personalColorTypeUpdateRequestDto);
        return ResponseDto.status(personalColorTypeResponseDto);
    }
  
    @DeleteMapping("/{personalColorTypeId}")
    public ResponseDto<?> deletePersonalColorType(HttpServletRequest httpServletRequest,
                                                    @PathVariable int personalColorTypeId){
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        consultPersonalColorService.deletePersonalColorType(consultantId, personalColorTypeId);
        return ResponseDto.status();
    }
}

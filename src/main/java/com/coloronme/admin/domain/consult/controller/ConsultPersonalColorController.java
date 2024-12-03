package com.coloronme.admin.domain.consult.controller;


import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeRequestDto;
import com.coloronme.admin.domain.consult.dto.request.PersonalColorTypeUpdateRequestDto;
import com.coloronme.admin.domain.consult.dto.response.ConsultPersonalColorResponseDto;
import com.coloronme.admin.domain.consult.service.ConsultPersonalColorService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.jwt.JwtUtil;
import com.coloronme.admin.domain.consult.dto.response.PersonalColorTypeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "퍼스널 컬러 타입 조회", description = "퍼스널 컬러 타입 조회")
    @GetMapping
    public ResponseDto<ConsultPersonalColorResponseDto> getPersonalColorType(HttpServletRequest httpServletRequest) {
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        ConsultPersonalColorResponseDto consultPersonalColorResponseDto = consultPersonalColorService.getPersonalColorType(consultantId);
        return ResponseDto.status(consultPersonalColorResponseDto);
    }

    @Operation(summary = "퍼스널 컬러 타입 등록", description = "퍼스널 컬러 타입 등록")
    @PostMapping
    public ResponseDto<PersonalColorTypeResponseDto> registerPersonalColorType(HttpServletRequest httpServletRequest,
                                                                             @Valid @RequestBody PersonalColorTypeRequestDto personalColorTypeRequestDto){
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        PersonalColorTypeResponseDto personalColorResponseDto = consultPersonalColorService
                .registerPersonalColorType(consultantId, personalColorTypeRequestDto);
        return ResponseDto.status(personalColorResponseDto);
    }

    @Operation(summary = "퍼스널 컬러 타입 수정", description = "퍼스널 컬러 타입 수정")
    @PatchMapping("/{personalColorTypeId}")
    public ResponseDto<PersonalColorTypeResponseDto> updatePersonalColorType(HttpServletRequest httpServletRequest,
                                                                             @PathVariable int personalColorTypeId,
                                                                             @RequestBody PersonalColorTypeUpdateRequestDto personalColorTypeUpdateRequestDto) {
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        PersonalColorTypeResponseDto personalColorTypeResponseDto = consultPersonalColorService
                .updatePersonalColorType(consultantId, personalColorTypeId, personalColorTypeUpdateRequestDto);
        return ResponseDto.status(personalColorTypeResponseDto);
    }

    @Operation(summary = "퍼스널 컬러 타입 삭제", description = "퍼스널 컬러 타입 삭제")
    @DeleteMapping("/{personalColorTypeId}")
    public ResponseDto<?> deletePersonalColorType(HttpServletRequest httpServletRequest,
                                                    @PathVariable int personalColorTypeId){
        int consultantId = jwtUtil.getIdFromRequest(httpServletRequest, "Access");
        consultPersonalColorService.deletePersonalColorType(consultantId, personalColorTypeId);
        return ResponseDto.status();
    }
}

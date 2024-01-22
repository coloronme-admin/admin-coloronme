package com.coloronme.admin.domain.mainpage.controller;

import com.coloronme.admin.domain.mainpage.dto.DataType;
import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.service.MainPageService;
import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.admin.global.exception.ErrorCode;
import com.coloronme.admin.global.exception.RequestException;
import com.coloronme.admin.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
public class MainPageController {

    private final MainPageService mainPageService;
    private final JwtUtil jwtUtil;

    @GetMapping("/data/users")
    public ResponseDto<?> getUserData(@ModelAttribute @Valid MainPageRequestDto mainPageRequestDto,
            HttpServletRequest request) {

        int consultantId = jwtUtil.getIdFromRequest(request, "Access");

        switch (mainPageRequestDto.getType()) {
            case "color", default -> {
                return ResponseDto.status(mainPageService.getUserDateByColor(consultantId, mainPageRequestDto));
            }
            case "channel" -> {
                return ResponseDto.status(mainPageService.getUserDataByChannel(consultantId, mainPageRequestDto));
            }
            case "gender" -> {
                return ResponseDto.status(mainPageService.getUserDataByGender(consultantId, mainPageRequestDto));
            }
            case "age" -> {
                return ResponseDto.status(mainPageService.getUserDataByAge(consultantId, mainPageRequestDto));
            }
            case "interval" -> {
                return ResponseDto.status(mainPageService.getUserDataByInterval(consultantId, mainPageRequestDto));
            }
            case "month" -> {
                return ResponseDto.status(mainPageService.getUserDataByMonth(consultantId, mainPageRequestDto));
            }
        }
    }
}

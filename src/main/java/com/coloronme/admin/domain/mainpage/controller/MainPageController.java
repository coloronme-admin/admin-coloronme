package com.coloronme.admin.domain.mainpage.controller;

import com.coloronme.admin.domain.mainpage.dto.DataType;
import com.coloronme.admin.domain.mainpage.dto.request.MainPageRequestDto;
import com.coloronme.admin.domain.mainpage.dto.response.MainPageResponseDto;
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
    public ResponseDto<MainPageResponseDto> getUserData(@ModelAttribute @Valid MainPageRequestDto mainPageRequestDto,
            HttpServletRequest request) {

        int consultantId = jwtUtil.getIdFromRequest(request, "Access");

        new MainPageResponseDto();
        MainPageResponseDto mainPageResponseDto = switch (mainPageRequestDto.getType()) {
            case "color" -> mainPageService.getUserDateByColor(consultantId, mainPageRequestDto);
            case "channel" -> mainPageService.getUserDataByChannel(consultantId, mainPageRequestDto);
            case "gender" -> mainPageService.getUserDataByGender(consultantId, mainPageRequestDto);
            case "age" -> mainPageService.getUserDataByAge(consultantId, mainPageRequestDto);
            case "interval" -> mainPageService.getUserDataByInterval(consultantId, mainPageRequestDto);
            case "month" -> mainPageService.getUserDataByMonth(consultantId, mainPageRequestDto);
            default -> new MainPageResponseDto();
        };

        return ResponseDto.status(mainPageResponseDto);
    }
}

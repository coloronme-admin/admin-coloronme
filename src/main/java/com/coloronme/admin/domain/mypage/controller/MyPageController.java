package com.coloronme.admin.domain.mypage.controller;

import com.coloronme.admin.domain.mypage.dto.request.MyPageRequestDto;
import com.coloronme.admin.domain.mypage.dto.response.MyPageResponseDto;
import com.coloronme.admin.domain.mypage.dto.request.PasswordRequestDto;
import com.coloronme.admin.domain.mypage.dto.response.PasswordResponseDto;
import com.coloronme.admin.domain.mypage.service.MyPageService;
import com.coloronme.admin.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequiredArgsConstructor
@Tag(name = "MyPage Controller" , description = "마이페이지 불러오기/수정/비밀번호 변경 컨트롤러")
public class MyPageController {
    private final MyPageService myPageService;

    @Operation(summary = "GetMyPage", description = "마이페이지 불러오기")
    @GetMapping("/myPages")
    public ResponseDto<MyPageResponseDto> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.getMyPage(userDetails.getUsername());
    }

    @Operation(summary = "UpdateMyPage", description = "마이페이지 수정")
    @PatchMapping("/myPages")
    public ResponseDto<?> updateMyPage(@RequestBody @Valid MyPageRequestDto myPageRequestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.updateMyPage(myPageRequestDto, userDetails.getUsername());
    }

    @Operation(summary = "UpdatePassword", description = "비밀번호 변경")
    @PatchMapping("/password")
    public ResponseDto<PasswordResponseDto> updatePassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.updatePassword(passwordRequestDto,userDetails.getUsername());
    }

}

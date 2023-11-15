package com.coloronme.admin.domain.mypage.controller;


import com.coloronme.admin.domain.mypage.dto.MyPageRequestDto;
import com.coloronme.admin.domain.mypage.dto.MyPageResponseDto;
//import com.coloronme.admin.domain.mypage.dto.PasswordRequestDto;
import com.coloronme.admin.domain.mypage.dto.PasswordRequestDto;
import com.coloronme.admin.domain.mypage.dto.PasswordResponseDto;
import com.coloronme.admin.domain.mypage.service.MyPageService;
import com.coloronme.admin.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/myPages")
    public ResponseDto<MyPageResponseDto> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.getMyPage(Integer.parseInt(userDetails.getUsername()));
    }

    @PatchMapping("/myPages")
    public ResponseDto<MyPageResponseDto> updateMyPage(@RequestBody MyPageRequestDto myPageRequestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.updateMyPage(myPageRequestDto, Integer.parseInt(userDetails.getUsername()));
    }

    @PatchMapping("/password")
    public ResponseDto<PasswordResponseDto> updatePassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.updatePassword(passwordRequestDto, Integer.parseInt(userDetails.getUsername()));
    }

}

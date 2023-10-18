package com.coloronme.admin.domain.mypage.controller;


import com.coloronme.admin.domain.mypage.dto.MyPageRequestDto;
import com.coloronme.admin.domain.mypage.dto.MyPageResponseDto;
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
        return myPageService.getMyPage(userDetails.getUsername());
    }

    @PatchMapping("/myPageSujung")
    public ResponseDto<?> updateMyPage(@RequestBody MyPageRequestDto myPageRequestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.updateMyPage(myPageRequestDto, userDetails.getUsername());
    }
}

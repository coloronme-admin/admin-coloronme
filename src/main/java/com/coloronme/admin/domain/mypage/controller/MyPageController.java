package com.coloronme.admin.domain.mypage.controller;

import com.coloronme.admin.domain.mypage.dto.MyPageRequestDto;
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

    //마이페이지 정보 불러오기
    @GetMapping("/myPages")
    public ResponseDto<?> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.getMyPage(userDetails.getUsername());
    }


    //마이페이지 수정하기
    @PatchMapping("/myPageSujung")
    public ResponseDto<?> patchMyPage(@RequestBody MyPageRequestDto myPageRequestDto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.patchMyPage(myPageRequestDto, userDetails.getUsername());
    }

}

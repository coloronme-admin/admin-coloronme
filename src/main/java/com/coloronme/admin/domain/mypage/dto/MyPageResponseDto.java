package com.coloronme.admin.domain.mypage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MyPageResponseDto {

    String name;
    String company;
    String email;
}

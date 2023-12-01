package com.coloronme.admin.domain.mypage.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MyPageResponseDto {

    @Schema
    String name;
    @Schema
    String company;
    @Schema
    String email;
}

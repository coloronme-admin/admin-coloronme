package com.coloronme.admin.domain.consultant.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {

    @Schema
    private String AccessToken;
    @Schema
    private String RefreshToken;

}

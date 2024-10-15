package com.coloronme.admin.domain.consultant.dto.response;

import com.coloronme.product.personalColor.entity.ColorGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {

    @Schema
    private String email;
    @Schema
    private ColorGroup colorGroup;
}


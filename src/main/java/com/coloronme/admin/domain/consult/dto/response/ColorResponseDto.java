package com.coloronme.admin.domain.consult.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColorResponseDto {
    private int colorId;
    private String name;
    private String r;
    private String g;
    private String b;
}

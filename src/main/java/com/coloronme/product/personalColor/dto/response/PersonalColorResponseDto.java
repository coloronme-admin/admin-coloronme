package com.coloronme.product.personalColor.dto.response;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalColorResponseDto {
    private List<ColorResponseDto> p;
    private List<ColorResponseDto> lt;
    private List<ColorResponseDto> b;
    private List<ColorResponseDto> v;
    private List<ColorResponseDto> s;
    private List<ColorResponseDto> sf;
    private List<ColorResponseDto> d;
    private List<ColorResponseDto> dp;
    private List<ColorResponseDto> dk;
    private List<ColorResponseDto> ltg;
    private List<ColorResponseDto> g;
    private List<ColorResponseDto> dkg;
}

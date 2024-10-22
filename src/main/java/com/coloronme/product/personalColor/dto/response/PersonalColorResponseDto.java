package com.coloronme.product.personalColor.dto.response;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.personalColor.dto.ColorGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalColorResponseDto {
    /*PCCS*/
    private List<ColorResponseDto> p; /*페일*/
    private List<ColorResponseDto> lt; /*라이트*/
    private List<ColorResponseDto> b; /*브라이트*/
    private List<ColorResponseDto> v; /*비비드*/
    private List<ColorResponseDto> s; /*스트롱*/
    private List<ColorResponseDto> sf; /*소프트*/
    private List<ColorResponseDto> d; /*덜*/
    private List<ColorResponseDto> dp; /*딥*/
    private List<ColorResponseDto> dk; /*다크*/
    private List<ColorResponseDto> ltg; /*라이트그레이쉬*/
    private List<ColorResponseDto> g; /*그레이쉬*/
    private List<ColorResponseDto> dkg; /*다크그레이쉬*/
    /*KS*/
    private List<ColorResponseDto> wh; /*화이티쉬*/
    private List<ColorResponseDto> pl; /*페일*/
    private List<ColorResponseDto> bs; /*베이직*/
    private List<ColorResponseDto> gk; /*다크*/
    private List<ColorResponseDto> bk; /*블랙키쉬*/

    public PersonalColorResponseDto(ColorGroup colorGroup) {
        if(colorGroup == ColorGroup.PCCS) {
            this.p = new ArrayList<>();
            this.lt = new ArrayList<>();
            this.b = new ArrayList<>();
            this.v = new ArrayList<>();
            this.s = new ArrayList<>();
            this.sf = new ArrayList<>();
            this.d = new ArrayList<>();
            this.dp = new ArrayList<>();
            this.dk = new ArrayList<>();
            this.ltg = new ArrayList<>();
            this.g = new ArrayList<>();
            this.dkg = new ArrayList<>();
        } else if(colorGroup == ColorGroup.KS) {
            this.wh = new ArrayList<>();
            this.pl = new ArrayList<>();
            this.lt = new ArrayList<>();
            this.v = new ArrayList<>();
            this.ltg = new ArrayList<>();
            this.sf = new ArrayList<>();
            this.g = new ArrayList<>();
            this.d = new ArrayList<>();
            this.bs = new ArrayList<>();
            this.dkg = new ArrayList<>();
            this.dp = new ArrayList<>();
            this.gk = new ArrayList<>();
            this.bk = new ArrayList<>();
        }
    }
}

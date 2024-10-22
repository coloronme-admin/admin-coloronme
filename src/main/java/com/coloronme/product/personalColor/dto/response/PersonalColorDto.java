package com.coloronme.product.personalColor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*DB에서 필요한 데이터를 받기위해 만든 Dto*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalColorDto {
    private String personalColorName;
    private Integer colorId;
    private String name; /*color name*/
    private String r;
    private String g;
    private String b;
}

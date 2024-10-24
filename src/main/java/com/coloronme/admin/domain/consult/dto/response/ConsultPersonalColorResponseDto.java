package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.personalColor.dto.response.PersonalColorTypeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConsultPersonalColorResponseDto {
    private List<PersonalColorTypeDto> spring;
    private List<PersonalColorTypeDto> summer;
    private List<PersonalColorTypeDto> fall;
    private List<PersonalColorTypeDto> winter;
}
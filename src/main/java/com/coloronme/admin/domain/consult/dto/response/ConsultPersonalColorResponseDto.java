package com.coloronme.admin.domain.consult.dto.response;

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
    private List<PersonalColorTypeDto> autumn;
    private List<PersonalColorTypeDto> winter;
}

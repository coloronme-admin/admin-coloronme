package com.coloronme.admin.domain.consult.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalColorTypeListResponseDto {
    private List<PersonalColorTypeDto> spring = new ArrayList<>();
    private List<PersonalColorTypeDto> summer = new ArrayList<>();
    private List<PersonalColorTypeDto> fall = new ArrayList<>();
    private List<PersonalColorTypeDto> winter = new ArrayList<>();
}

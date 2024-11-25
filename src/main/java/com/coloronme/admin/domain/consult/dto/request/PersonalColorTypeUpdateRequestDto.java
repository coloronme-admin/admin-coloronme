package com.coloronme.admin.domain.consult.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PersonalColorTypeUpdateRequestDto {
    private String personalColorTypeName;
    private List<Integer> colors;
}

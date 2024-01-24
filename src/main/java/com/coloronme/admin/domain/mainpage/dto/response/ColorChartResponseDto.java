package com.coloronme.admin.domain.mainpage.dto.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColorChartResponseDto {
    private String name;
    private int count;
    private double percentage;
}

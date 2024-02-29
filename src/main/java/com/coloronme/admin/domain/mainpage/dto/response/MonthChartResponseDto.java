package com.coloronme.admin.domain.mainpage.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthChartResponseDto {
    private List<Integer> period = new ArrayList<>();
    @JsonProperty(value = "increase_than_right_before")
    private double increaseThanRightBefore;
    @JsonProperty(value = "full_period")
    private double fullPeriod;
}

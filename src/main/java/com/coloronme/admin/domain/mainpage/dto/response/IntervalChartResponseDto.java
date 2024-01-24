package com.coloronme.admin.domain.mainpage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntervalChartResponseDto {
    private String name;
    private List<IntervalChartData> data = new LinkedList<>();

    public IntervalChartResponseDto(int hour) {
        for(int i=0 ; i<hour ; i++) {
            IntervalChartData intervalChartData = new IntervalChartData();
            intervalChartData.setTime(i);
            this.data.add(intervalChartData);
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IntervalChartData {
        private int time;
        private int count;
    }
}

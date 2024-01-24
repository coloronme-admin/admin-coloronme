package com.coloronme.admin.domain.mainpage.dto.response;

public class IntervalChartResponseDto {
    private String name;
    private IntervalChartData intervalChartData;

    static class IntervalChartData {
        private int time;
        private int count;
    }
}

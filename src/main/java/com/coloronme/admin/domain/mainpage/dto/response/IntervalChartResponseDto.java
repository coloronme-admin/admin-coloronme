package com.coloronme.admin.domain.mainpage.dto.response;

import com.coloronme.admin.domain.mainpage.dto.PrincipalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntervalChartResponseDto {
    private String id;
    private List<IntervalChartData> data = new LinkedList<>();

    public IntervalChartResponseDto(PrincipalType principalType, int number) {
        if(principalType.equals(PrincipalType.DAY)){
            for (int i = 0; i < number; i++) {
                IntervalChartData intervalChartData = new IntervalChartData();
                intervalChartData.setX(String.valueOf(i));
                this.data.add(intervalChartData);
            }
        } else {
            DayOfWeek day = DayOfWeek.SUNDAY;
            for (int i = 0; i < number; i++) {
                IntervalChartData intervalChartData = new IntervalChartData();
                intervalChartData.setX(day.plus(i).toString());
                this.data.add(intervalChartData);
            }
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IntervalChartData {
        private String x;
        private int y;
    }
}

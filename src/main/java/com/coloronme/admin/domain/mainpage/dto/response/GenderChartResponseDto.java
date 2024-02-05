package com.coloronme.admin.domain.mainpage.dto.response;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderChartResponseDto {

	private int male;
	private int female;
	private int unknown;
}


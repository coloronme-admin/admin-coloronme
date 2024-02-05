package com.coloronme.admin.domain.mainpage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgeChartResponseDto {

	private int ten;
	private int twenty;
	private int thirty;
	private int forty;
	private int fifty;
	private int sixtyOver;
}

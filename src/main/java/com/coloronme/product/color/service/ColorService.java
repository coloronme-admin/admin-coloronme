package com.coloronme.product.color.service;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.color.entity.Color;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {
    public ColorResponseDto getColorResponseDto(Color color) {
        return ColorResponseDto.builder()
                .colorId(color.getId())
                .name(color.getName())
                .r(color.getR())
                .g(color.getG())
                .b(color.getB())
                .build();
    }
}

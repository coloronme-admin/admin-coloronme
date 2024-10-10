package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.color.entity.Color;
import com.coloronme.product.member.enums.GenderEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultUserResponseDto {
    private int memberId;
    private String nickname;
    private String email;
    private String profileImageUrl;
    private LocalDateTime consultedDate;
    private Integer personalColorId;
    private List<ColorResponseDto> colors;
    private Integer personalColorTypeId;
    private Integer age;
    private GenderEnum genderEnum;
    private String consultedContent;
    private String consultedDrawing;
    private String uuid;
    private String consultedFile;
}
  
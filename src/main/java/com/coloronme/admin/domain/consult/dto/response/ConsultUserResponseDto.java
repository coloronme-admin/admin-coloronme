package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.color.dto.ColorResponseDto;
import com.coloronme.product.member.enums.GenderEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultUserResponseDto {
    private int memberId;
    private String nickname;
    private String email;
    private String profileImageUrl;
    private LocalDateTime consultedDate;
    private Integer age;
    private GenderEnum genderEnum;
    private String consultedContent;
    private String consultedDrawing;
    private String personalColorGroupName;
    private PersonalColorTypeDto personalColorType;
    private String consultedFile;
    private String uuid;
}
  
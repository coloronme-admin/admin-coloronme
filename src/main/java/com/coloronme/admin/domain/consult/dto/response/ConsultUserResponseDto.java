package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.member.enums.GenderEnum;
import lombok.*;

import java.time.LocalDateTime;

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
    private String age;
    private GenderEnum genderEnum;
    private String consultedContent;
    private String consultedDrawing;
    private String uuid;
    private String consultedFile;
}
  
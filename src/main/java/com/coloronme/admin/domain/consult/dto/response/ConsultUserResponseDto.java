package com.coloronme.admin.domain.consult.dto.response;

import com.coloronme.product.member.enums.Gender;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultUserResponseDto {
    private String nickname;
    private String email;
    private LocalDateTime personalDate;
    private Integer personalColorId;
    private Integer age;
    private Gender gender;
    private String consultContent;
    private String consultDrawing;
}
  
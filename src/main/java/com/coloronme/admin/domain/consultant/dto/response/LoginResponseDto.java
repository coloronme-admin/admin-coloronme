package com.coloronme.admin.domain.consultant.dto.response;


import com.coloronme.admin.domain.consultant.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private RoleType roleType;
}

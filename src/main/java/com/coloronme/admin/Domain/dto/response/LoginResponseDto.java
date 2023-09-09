package com.coloronme.admin.Domain.dto.response;


import com.coloronme.admin.Domain.entity.Authority;
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
    private Authority authority;

}

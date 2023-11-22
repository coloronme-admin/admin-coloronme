package com.coloronme.admin.domain.consultant.dto.response;


import com.coloronme.admin.domain.consultant.entity.RoleType;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    @Schema
    private String email;
    @Schema
    private RoleType roleType;
}

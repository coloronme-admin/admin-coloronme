package com.coloronme.admin.domain.consultant.dto.response;


import com.coloronme.admin.domain.consultant.entity.RoleType;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

//    @ApiModelProperty(example = "관리자 이메일")
    private String email;
    private RoleType roleType;
}

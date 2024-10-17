package com.coloronme.product.personalColor.controller;

import com.coloronme.admin.global.dto.ResponseDto;
import com.coloronme.product.personalColor.dto.PersonalColorResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/color")
@RestController
public class PersonalColorController {

    /*색상군 조회*/
    @GetMapping("/group")
    public ResponseDto<PersonalColorResponseDto> getColorGroupList(){


        return null;
    }
}

package com.coloronme.admin.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/health")
    public String checkHealth(){
        return "Connection Status is Ok.";
    }
}

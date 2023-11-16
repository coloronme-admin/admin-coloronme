package com.coloronme.admin.global.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Coloronme Admin API",
                description = "Coloronme Admin API 명세서입니다.",
                version = "V1"
        )
)
@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {

//        return new OpenAPI()
//                .addSecurityItem()
//                .components();
        return new OpenAPI();
    }
}

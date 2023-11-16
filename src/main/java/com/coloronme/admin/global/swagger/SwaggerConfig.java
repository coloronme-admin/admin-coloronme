package com.coloronme.admin.global.swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "",
                description = "",
                version = ""
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

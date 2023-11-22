package com.coloronme.admin.global.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("Coloronme Admin API")
                .description("Coloronme Admin API 명세서입니다.")
                .version("V2");

        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");


        return new OpenAPI()
                .components(new Components().addSecuritySchemes("JWT", bearerAuth))
                .addSecurityItem(addSecurityItem)
                .info(info);
    }
}

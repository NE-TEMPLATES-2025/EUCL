package com.paccy.eucl.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String securityScheme= "bearerAuth";
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Eucl API")
                        .description("EUCL application API")

        ).addSecurityItem(
                new SecurityRequirement(

                )
                        .addList(securityScheme)

        )
                .components(
                        new Components()
                                .addSecuritySchemes(securityScheme,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .bearerFormat("JWT")
                                                .name(securityScheme)
                                                .in(SecurityScheme.In.HEADER)
                                                .scheme("bearer")
                                )

                ).externalDocs(
                        new ExternalDocumentation()
                                .description("EUCL application API")
                );
    }
}

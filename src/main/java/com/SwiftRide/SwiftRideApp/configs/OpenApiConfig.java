package com.SwiftRide.SwiftRideApp.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";


    @Bean
    public OpenAPI swiftDriveOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swift Drive API")
                        .version("v1.0")
                        .description("API documentation for Swift Drive application")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Nishant")
                                .email("nishant.narula023@gmail.com"))
                ).addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // (Optional) If you want to group APIs (like user/contact)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Swift-Drive")
                .pathsToMatch("/api/**")
                .build();
    }

}

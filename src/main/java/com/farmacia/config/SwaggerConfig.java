package com.farmacia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Farmacia")
                        .version("1.0")
                        .description("API para el sistema de gestión de farmacia")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@farmacia.com")));
    }
}
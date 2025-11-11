package com.farmacia.config;

// Swagger dependencies commented out in build.gradle for deployment
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.Contact;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Comentado temporalmente para resolver problemas de despliegue
// public class SwaggerConfig {
public class SwaggerConfigDisabled {

    // Comentado temporalmente para resolver problemas de despliegue
    /*
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
    */
}
package com.farmacia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins:http://localhost:3000}")
    private String allowedOriginsString;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] allOrigins = Arrays.stream(allowedOriginsString.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

                List<String> exactOrigins = new ArrayList<>();
                List<String> patternOrigins = new ArrayList<>();

                for (String origin : allOrigins) {
                    if (origin.contains("*")) {
                        patternOrigins.add(origin);
                    } else {
                        exactOrigins.add(origin);
                    }
                }

                registry.addMapping("/**")
                        .allowedOrigins(exactOrigins.toArray(new String[0]))
                        .allowedOriginPatterns(patternOrigins.toArray(new String[0]))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
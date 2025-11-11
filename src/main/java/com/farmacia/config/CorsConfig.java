package com.farmacia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins:http://localhost:3000}")
    private String allowedOriginsString;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Parse origins
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

        // Debug logging
        System.out.println("CORS Config - Allowed Origins String: " + allowedOriginsString);
        System.out.println("CORS Config - Exact Origins: " + exactOrigins);
        System.out.println("CORS Config - Pattern Origins: " + patternOrigins);

        // Set origins
        config.setAllowedOrigins(exactOrigins);
        config.setAllowedOriginPatterns(patternOrigins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

package com.farmacia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF (necesario para APIs REST)
                .cors(cors -> {}) // Usa la configuración de CORS global que haremos abajo
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**", "/ws/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}

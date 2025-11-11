package com.farmacia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins:http://localhost:3000}")
    private String allowedOriginsString;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
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
        System.out.println("CORS Filter - Allowed Origins String: " + allowedOriginsString);
        System.out.println("CORS Filter - Exact Origins: " + exactOrigins);
        System.out.println("CORS Filter - Pattern Origins: " + patternOrigins);

        // Set origins
        config.setAllowedOrigins(exactOrigins);
        config.setAllowedOriginPatterns(patternOrigins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter(source));
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> corsLoggingFilterRegistration() {
        OncePerRequestFilter loggingFilter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                String origin = request.getHeader("Origin");
                String method = request.getMethod();

                System.out.println("CORS Request - Origin: " + origin + ", Method: " + method + ", URL: " + request.getRequestURL());

                filterChain.doFilter(request, response);

                String acao = response.getHeader("Access-Control-Allow-Origin");
                System.out.println("CORS Response - Access-Control-Allow-Origin: " + acao);
            }
        };

        FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loggingFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1); // Just after CORS
        return registrationBean;
    }
}
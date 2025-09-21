/**

package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Permitir acceso público a Swagger y endpoints de API
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/api/catalogo/**",
                                "/api/categorias/**"
                        ).permitAll()
                        // El resto de endpoints requieren autenticación
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs
                .formLogin(form -> form.disable()) // Deshabilitar formulario de login
                .httpBasic(httpBasic -> httpBasic.disable()); // Deshabilitar autenticación básica

        return http.build();
    }
}
 **/
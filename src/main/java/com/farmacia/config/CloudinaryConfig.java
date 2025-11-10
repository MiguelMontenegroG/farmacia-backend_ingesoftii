package com.farmacia.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    
    @Bean
    public Cloudinary cloudinary() {
        // En entorno académico, usamos configuración básica
        // En producción, estos valores deben venir de variables de entorno
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", System.getenv().getOrDefault("CLOUDINARY_CLOUD_NAME", "farmacia-demo"),
            "api_key", System.getenv().getOrDefault("CLOUDINARY_API_KEY", "123456789"),
            "api_secret", System.getenv().getOrDefault("CLOUDINARY_API_SECRET", "secret-key"),
            "secure", true
        ));
    }
}
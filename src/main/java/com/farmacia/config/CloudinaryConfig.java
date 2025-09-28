package com.farmacia.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtcpxlmbk",
                "api_key", "288135791234882",
                "api_secret", "Z3CwqiA05Rx5jDhlPzzSOzeex04"));
    }
}

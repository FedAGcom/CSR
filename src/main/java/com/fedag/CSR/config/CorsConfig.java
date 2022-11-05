package com.fedag.CSR.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")

                .allowCredentials(true)

//                .allowedOrigins("*");

                    //TODO Убрать в проде localhost
                .allowedOrigins("http://csgofarm.online:3000", "http://localhost:3000", "https://steamcommunity.com"
                ,"http://localhost:8080", "http://localhost");
    }
}
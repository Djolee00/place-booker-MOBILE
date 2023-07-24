package com.placebooker.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(
            "http://localhost:8100",
            "http://localhost") // Replace with the actual origin of your frontend application
        .allowedMethods("*")
        .allowedHeaders("*")
        .allowCredentials(true)
        .exposedHeaders("Authorization");
  }
}

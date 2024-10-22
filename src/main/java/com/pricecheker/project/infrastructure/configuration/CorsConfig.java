package com.pricecheker.project.infrastructure.configuration;

/*
    Author: juannegrin
    Date: 21/10/24
    Time: 21:48
*/

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**") // Permitir CORS en todas las rutas
        .allowedOrigins("*") // Cambia esto al origen de tu frontend
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
  }
}

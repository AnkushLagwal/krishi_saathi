package com.example.krishisaathi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map all requests to /uploads/** to the local "uploads/" folder
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");  
    }

}

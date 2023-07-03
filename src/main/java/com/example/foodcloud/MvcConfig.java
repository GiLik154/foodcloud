package com.example.foodcloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File path = new File(".");

        try {
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:" + path.getCanonicalPath() + "/images/");
            registry.addResourceHandler("/css/**")
                    .addResourceLocations("classpath:/static/css/");
            registry.addResourceHandler("/js/**")
                    .addResourceLocations("classpath:/static/js/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
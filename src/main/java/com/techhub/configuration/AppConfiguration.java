package com.techhub.configuration;

import com.techhub.aspect.LoggingAspect;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    private static final long MAX_AGE_SECS = 1800;

    private static final Logger logger = LogManager.getLogger(AppConfiguration.class);

    static {
        Thread thread = new Thread(() -> {
            while(true){
                logger.debug("Test: " + System.currentTimeMillis());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    logger.error("Thread interrupted!");
                }
            }
        });

        thread.start();
    }

    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .allowCredentials(false)
                .maxAge(MAX_AGE_SECS);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

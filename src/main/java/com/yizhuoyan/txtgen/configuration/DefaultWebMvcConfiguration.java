package com.yizhuoyan.txtgen.configuration;

import com.yizhuoyan.txtgen.configuration.spring.converter.ValueDisplayNameEnumConverterFactory;
import com.yizhuoyan.txtgen.configuration.spring.converter.LocalDateFormatter;
import com.yizhuoyan.txtgen.configuration.spring.converter.LocalDateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultWebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new ValueDisplayNameEnumConverterFactory<>());
        registry.addFormatter(new LocalDateTimeFormatter());
        registry.addFormatter(new LocalDateFormatter());
    }
}
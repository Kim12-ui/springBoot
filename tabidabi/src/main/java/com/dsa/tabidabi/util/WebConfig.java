package com.dsa.tabidabi.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/representativeimage/**") // 웹 브라우저에서 접근할 경로
                .addResourceLocations("file:///c:/teamproject/representativeimage/"); // 실제 파일이 저장된 경로
    }
}

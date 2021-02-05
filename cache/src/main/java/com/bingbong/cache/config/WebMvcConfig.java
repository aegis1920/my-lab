package com.bingbong.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 해당 정적 파일에 Cache-Control 추가
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
            .addResourceLocations("classpath:/static/")
            .setUseLastModified(true) // Last Modified는 기본값이 true
            .setCachePeriod(60 * 60 * 24 * 365); // maxAge 기간

        registry.addResourceHandler("/nothing.html")
            .addResourceLocations("classpath:/static/")
            .setUseLastModified(false);
    }
}

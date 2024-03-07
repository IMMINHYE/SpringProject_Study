package com.example.study002.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // application.properties에서 설정한 업로드 경로를 주입받음
    @Value("${uploadPath}")
    String uploadPath;

    // 이미지 리소스 핸들러를 추가하여 업로드된 이미지에 접근할 수 있도록 함
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/image/**")
                .addResourceLocations(uploadPath);
    }
}

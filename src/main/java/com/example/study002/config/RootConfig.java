package com.example.study002.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    // ModelMapper 빈 등록
    @Bean
    public ModelMapper getMappder() {
        // ModelMapper 인스턴스 생성
        ModelMapper modelMapper = new ModelMapper();

        // ModelMapper 설정
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)      // 필드 매칭 활성화
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)      // 접근 레벨 설정 (private)
                .setMatchingStrategy(MatchingStrategies.LOOSE);     // 매칭 전략 설정 (LOOSE)
        return modelMapper;     // 설정된 ModelMapper 반환
    }
}

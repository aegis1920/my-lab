package com.bingbong.timezonediff.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
        basePackages = {"com.bingbong.timezonediff"}
)
public class MybatisConfig {
}

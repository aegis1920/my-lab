package com.bingbong.springdatajpa;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing(modifyOnCreate = false) 이 옵션을 주면 생성할 때 update 쪽에 null이 들어간다.
@EnableJpaAuditing
@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        // 실무에서는 스프링 시큐리티 컨텍스트 홀더(세션같은 곳)에서 ID를 꺼내온다.
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}

package com.bingbong.cache.service;


import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeServiceCacheTest {

    private static final Logger log = LoggerFactory.getLogger(HomeServiceCacheTest.class);

    @Autowired
    private HomeWithCacheService homeWithCacheService;

    @Autowired
    private HomeWithoutCacheService homeWithoutCacheService;

    @BeforeEach
    void setUp() {
        homeWithCacheService.create("123-123");
        homeWithoutCacheService.create("123-123");
    }

    @Test
    void applicationCacheTest() {
        calculateMethodTime(() -> homeWithCacheService.findByIdWithCache(1L));
        calculateMethodTime(() -> homeWithCacheService.findByIdWithCache(1L));
        log.info("------------------------------------------------");
        calculateMethodTime(() -> homeWithoutCacheService.findByIdWithCache(1L));
        calculateMethodTime(() -> homeWithoutCacheService.findByIdWithCache(1L));
    }

    private void calculateMethodTime(Supplier method) {
        long startTime = System.currentTimeMillis();
        method.get();
        long endTime = System.currentTimeMillis();

        log.info("현재 메서드의 사용 시간은 {} 입니다!", endTime - startTime);
    }

}

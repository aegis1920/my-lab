package com.bingbong.springdataredis.preventduplicatedrequest.service;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean setEx(String key, Object value, Long expiredAt, TimeUnit timeUnit) {
        try {
            return redisTemplate.opsForValue()
                .setIfAbsent(key, value, expiredAt, timeUnit);
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean delete(String key) {

        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            return false;
        }
    }
}

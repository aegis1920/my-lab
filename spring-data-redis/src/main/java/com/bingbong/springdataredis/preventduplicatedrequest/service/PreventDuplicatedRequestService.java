package com.bingbong.springdataredis.preventduplicatedrequest.service;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class PreventDuplicatedRequestService {

    // 10초 동안 중복되는 Request를 막음. 10초가 지나면 알아서 사라짐
    private static final Long REDIS_KEY_DEFAULT_LOCK_TIME_MILLIS = 10000L;

    private final RedisService redisService;

    public PreventDuplicatedRequestService(RedisService redisService) {
        this.redisService = redisService;
    }

    public Boolean tryLock(String lockKey) {
        return redisService.setEx(lockKey, "lock", REDIS_KEY_DEFAULT_LOCK_TIME_MILLIS, TimeUnit.MILLISECONDS);
    }

    public Boolean tryLock(String lockKey, Long milliseconds) {
        return redisService.setEx(lockKey, "lock", milliseconds, TimeUnit.MILLISECONDS);
    }

    public Boolean tryLock(String lockKey, Object value, Long milliseconds) {
        return redisService.setEx(lockKey, value, milliseconds, TimeUnit.MILLISECONDS);
    }

    public Boolean unLock(String lockKey) {
        return redisService.delete(lockKey);
    }
}

package com.bingbong.springdataredis.preventduplicatedrequest.controller;

import com.bingbong.springdataredis.preventduplicatedrequest.service.PreventDuplicatedRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedisController {

    private final PreventDuplicatedRequestService preventDuplicatedRequestService;

    public RedisController(PreventDuplicatedRequestService preventDuplicatedRequestService) {
        this.preventDuplicatedRequestService = preventDuplicatedRequestService;
    }

    @GetMapping("/api/lock/{txId}")
    public String lockTest(@PathVariable("txId") Long txId) {
        String lockKey = "lockKey" + txId;
        Boolean isNotLocked = preventDuplicatedRequestService.tryLock(lockKey);

        if (!isNotLocked) {
            log.warn("중복 요청입니다! lockKey = {}", lockKey);
            return "DUPLICATED REQUEST";
        }
        return "SUCCESS RESPONSE";
    }
}

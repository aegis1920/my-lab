package com.bingbong.cache.service;

import com.bingbong.cache.domain.HomeWithoutCache;
import com.bingbong.cache.repository.HomeWithoutCacheRepository;
import org.springframework.stereotype.Service;

@Service
public class HomeWithoutCacheService {

    private final HomeWithoutCacheRepository homeWithoutCacheRepository;

    public HomeWithoutCacheService(
        HomeWithoutCacheRepository homeWithoutCacheRepository) {
        this.homeWithoutCacheRepository = homeWithoutCacheRepository;
    }



    public void create(String address) {
        homeWithoutCacheRepository.save(new HomeWithoutCache(address));
    }

    public HomeWithoutCache findByIdWithCache(Long id) {
        return homeWithoutCacheRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }
}

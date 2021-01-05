package com.bingbong.cache.service;

import com.bingbong.cache.domain.HomeWithCache;
import com.bingbong.cache.repository.HomeWithCacheRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class HomeWithCacheService {

    private final HomeWithCacheRepository homeWithCacheRepository;

    public HomeWithCacheService(HomeWithCacheRepository homeWithCacheRepository) {
        this.homeWithCacheRepository = homeWithCacheRepository;
    }

    public List<HomeWithCache> findAll() {
        return homeWithCacheRepository.findAll();
    }

    public void create(String address) {
        homeWithCacheRepository.save(new HomeWithCache(address));
    }

    @Cacheable("home")
    public HomeWithCache findByIdWithCache(Long id) {
        return homeWithCacheRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }

    public HomeWithCache findByIdWithoutCache(Long id) {
        return homeWithCacheRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }
}

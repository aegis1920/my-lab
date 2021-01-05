package com.bingbong.cache.repository;

import com.bingbong.cache.domain.HomeWithCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeWithCacheRepository extends JpaRepository<HomeWithCache, Long> {

}

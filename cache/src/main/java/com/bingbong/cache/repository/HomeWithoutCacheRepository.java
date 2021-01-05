package com.bingbong.cache.repository;

import com.bingbong.cache.domain.HomeWithoutCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeWithoutCacheRepository extends JpaRepository<HomeWithoutCache, Long> {

}

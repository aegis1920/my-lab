package com.bingbong.cache.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HomeWithoutCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    public HomeWithoutCache() {
    }

    public HomeWithoutCache(String address) {
        this.address = address;
    }
}

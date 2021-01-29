package com.bingbong.springdatajpa.repository;

import com.bingbong.springdatajpa.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

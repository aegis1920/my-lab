package com.bingbong.springdatajpa.repository;

import com.bingbong.springdatajpa.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void save() {
        Item item = new Item("A");
        itemRepository.save(item);
    }
}

package com.bingbong.jpabook.jpashop.respository;

import com.bingbong.jpabook.jpashop.domain.item.Item;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            // 새거니까 새로 영속화하는 과정
            em.persist(item);
        } else {
            // 일단은 update로 이해
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}

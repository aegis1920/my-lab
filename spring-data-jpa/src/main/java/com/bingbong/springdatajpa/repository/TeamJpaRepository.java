package com.bingbong.springdatajpa.repository;

import com.bingbong.springdatajpa.domain.Team;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Team save(Team Team) {
        em.persist(Team);
        return Team;
    }

    public void delete(Team Team) {
        em.remove(Team);
    }

    public List<Team> findAll() {
        // 전체조회는 JPQL을 적용해야함
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }

    public Optional<Team> findById(Long id) {
        Team Team = em.find(Team.class, id);
        return Optional.ofNullable(Team);
    }

    public long count() {
        return em.createQuery("select count(t) from Team t", Long.class)
            .getSingleResult(); // 단건일 경우 Single
    }

    public Team find(Long id) {
        return em.find(Team.class, id);
    }
}

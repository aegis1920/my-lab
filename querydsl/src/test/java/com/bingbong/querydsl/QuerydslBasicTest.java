package com.bingbong.querydsl;

import static com.bingbong.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

import com.bingbong.querydsl.entity.Member;
import com.bingbong.querydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void startJPQL() {
        // 문자열로 작성됨
        String qlString = "select m from Member m where m.username = :username";

        // member1을 찾아라
        Member findMember = em
            .createQuery(qlString, Member.class)
            .setParameter("username", "member1")
            .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl() {
        Member findMember = queryFactory
            .select(member)
            .from(member)
            .where(member.username.eq("member1")) // 파라미터 바인딩
            .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
}

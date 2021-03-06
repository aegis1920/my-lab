package com.bingbong.springdatajpa.domain;

import com.bingbong.springdatajpa.repository.MemberRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist((member1));
        em.persist((member2));
        em.persist((member3));
        em.persist((member4));

        em.flush();
        em.clear(); // JPA에 있는 영속성 캐시를 날림. 깔끔하게 반영이 됨

        List<Member> members = em.createQuery("select m from Member m", Member.class)
            .getResultList();

        for (Member member : members) {
            System.out.println(member);
            System.out.println(member.getTeam());
        }

    }

    @Test
    void JpaEventBaseEntity() throws InterruptedException {
        Member member = new Member("member1");
        memberRepository.save(member); // @PrePersist

        Thread.sleep(100);
        member.setUsername("member2");
        em.flush(); // @PreUpdate
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();

        System.out.println(findMember.getCreateDate());
        System.out.println(findMember.getLastModifiedDate());
        System.out.println(findMember.getCreatedBy());
        System.out.println(findMember.getLastModifiedBy());
    }
}

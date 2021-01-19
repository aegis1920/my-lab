package com.bingbong.jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.bingbong.jpabook.jpashop.domain.Member;
import com.bingbong.jpabook.jpashop.respository.MemberRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 얘 때문에 Rollback이 되어서 insert 쿼리가 나가지 않는다.
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    void 회원가입() {
        Member member = new Member();
        member.setName("bingbong");

        Long id = memberService.join(member);
//        Member member = memberRepository.findOne(id);

//        em.flush(); // 실제 쿼리를 보고 싶다면 이렇게 em.flush를 해주거나 @Test에 Rollback(false)를 주자.

        assertThat(member.getId()).isEqualTo(id);
    }

    @Test
    void 중복회원예외() {
        Member member1 = new Member();
        member1.setName("bingbong");

        Member member2 = new Member();
        member2.setName("bingbong");

        memberService.join(member1);

        assertThatThrownBy(() -> memberService.join(member2))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

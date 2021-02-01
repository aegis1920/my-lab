package com.bingbong.querydsl;

import static com.bingbong.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

import com.bingbong.querydsl.dto.MemberDto;
import com.bingbong.querydsl.dto.QMemberDto;
import com.bingbong.querydsl.dto.UserDto;
import com.bingbong.querydsl.entity.Member;
import com.bingbong.querydsl.entity.QMember;
import com.bingbong.querydsl.entity.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QuerydslIntermediateTest {

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
    void simpleProjection() {
        List<String> result = queryFactory
            .select(member.username)
            .from(member)
            .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void tupleProjection() {
        List<Tuple> result = queryFactory
            .select(member.username, member.age)
            .from(member)
            .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);

            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    @Test
    void findDtoByJPQL() {
        List<MemberDto> result = em.createQuery(
            "select new com.bingbong.querydsl.dto.MemberDto(m.username, m.age) from Member m",
            MemberDto.class)
            .getResultList();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findDtoBySetter() {
        List<MemberDto> result = queryFactory
            .select(Projections.bean(
                MemberDto.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findDtoByField() {
        List<MemberDto> result = queryFactory
            .select(Projections.fields(
                MemberDto.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findDtoByConstructor() {
        List<MemberDto> result = queryFactory
            .select(Projections.constructor(
                MemberDto.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findUserDtoByField() {
        QMember memberSub = new QMember("memberSub");

        List<UserDto> result = queryFactory
            .select(Projections.fields(
                UserDto.class,
                member.username.as("name"),

                ExpressionUtils.as(JPAExpressions
                    .select(memberSub.age.max())
                    .from(memberSub), "age")))
            .from(member)
            .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    void findUserDtoByConstructor() {
        List<UserDto> result = queryFactory
            .select(Projections.constructor(
                UserDto.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    void findDtoByQueryProjection() {
        List<MemberDto> result = queryFactory
            .select(new QMemberDto(member.username, member.age))
            .from(member)
            .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void dynamicQueryBooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {

        BooleanBuilder builder = new BooleanBuilder();

        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }

        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }
        return queryFactory
            .selectFrom(member)
            .where(builder)
            .fetch();
    }

    @Test
    void dynamicQueryWhere() {
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
            .selectFrom(member)
//            .where(usernameEq(usernameCond), ageEq(ageCond)) // null이라면 무시가 된다. 그래서 동적쿼리가 됨
            .where(allEq(usernameCond, ageCond)) // 이렇게 조합할 수도 있다. 매우 큰 장점
            .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private Predicate allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    void bulkUpdate() {

        // member1 = 10 -> DB member1
        // member2 = 10 -> DB member2
        // member3 = 10 -> DB member3
        // member4 = 10 -> DB member4

        long count = queryFactory
            .update(member)
            .set(member.username, "비회원")
            .where(member.age.lt(28))
            .execute();

        em.flush();
        em.clear();

        // member1 = 10 -> DB 비회원
        // member2 = 10 -> DB 비회원
        // member3 = 10 -> DB member3
        // member4 = 10 -> DB member4

        List<Member> result = queryFactory
            .selectFrom(member)
            .fetch();

        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }
    }

    @Test
    void bulkAdd() {
        long count = queryFactory
            .update(member)
            .set(member.age, member.age.add(1))
            .execute();
    }

    @Test
    void bulkDelete() {
        long count = queryFactory
            .delete(member)
            .where(member.age.gt(10))
            .execute();
    }

    @Test
    void sqlFunction() {
        List<String> result = queryFactory
            .select(
                Expressions.stringTemplate(
                    "function('replace', {0}, {1}, {2})",
                    member.username, "member", "M"))
            .from(member)
            .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void sqlFunction2() {
        List<String> result = queryFactory
            .select(member.username)
            .from(member)
//            .where(member.username
//                .eq(Expressions.stringTemplate("function('lower', {0})", member.username)))
            .where(member.username.eq(member.username.lower())) // 위 코드와 같은 기능이다
            .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
}

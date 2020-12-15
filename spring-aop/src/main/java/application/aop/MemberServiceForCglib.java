package application.aop;

import application.domain.Member;
import org.springframework.stereotype.Component;

// Spring AOP를 이용한 방법
@Component
public class MemberServiceForCglib {

    // 메서드가 메서드를 호출하면 프록시 객체가 아니라서 공통 로직이 먹지 않는다.
    // @PerformanceCheck
    public void inner() {
        System.out.println("내부 메서드입니당");
    }

    @PerformanceCheck
    public Member create(String name) {
        inner();
        return new Member(name);
    }
}

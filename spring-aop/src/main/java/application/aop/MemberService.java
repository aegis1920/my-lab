package application.aop;

import application.domain.Member;
import org.springframework.stereotype.Component;

@Component
// Spring AOP를 이용한 방법
public class MemberService {

    public Member create(String name) {
        return new Member(name);
    }
}

package application.cgllibproxy;

import application.domain.Member;

public class MemberServiceImpl {

    public Member create(String name) {
        return new Member(name);
    }
}


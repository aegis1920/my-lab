package application.proxypattern;

import application.domain.Member;

public class MemberServiceImpl implements MemberService {

    @Override
    public Member create(String name) {
        return new Member(name);
    }
}

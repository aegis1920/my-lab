package application.proxypattern;

import application.domain.Member;

public interface MemberService {
    Member create(String name);
}

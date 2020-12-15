package application.aop;

import application.domain.Member;

public interface MemberServiceForJdkDynamic {

    void inner();

    Member create(String name);
}

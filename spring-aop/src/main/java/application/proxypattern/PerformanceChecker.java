package application.proxypattern;

import application.domain.Member;

public class PerformanceChecker implements MemberService {

    MemberService memberService = new MemberServiceImpl();

    @Override
    public Member create(String name) {
        long start = System.currentTimeMillis();
        Member member = memberService.create(name);
        long end = System.currentTimeMillis();

        System.out.println("수행 시간 : " + (end - start));
        
        return member;
    }
}

package com.bingbong.core;

import com.bingbong.core.member.Grade;
import com.bingbong.core.member.Member;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
	public static void main(String[] args) {
//		AppConfig appConfig = new AppConfig();
//		MemberService memberService = appConfig.memberService();
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		
		Member memberA = new Member(1L, "memberA", Grade.VIP);
		memberService.join(memberA);
		
		Member byMember = memberService.findByMember(1L);
		
	}
}

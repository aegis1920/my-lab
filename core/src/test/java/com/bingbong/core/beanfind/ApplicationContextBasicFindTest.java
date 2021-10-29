package com.bingbong.core.beanfind;

import com.bingbong.core.AppConfig;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationContextBasicFindTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@DisplayName("빈 이름으로 조회")
	@Test
	void findBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@DisplayName("이름 없이 타입으로만 조회")
	@Test
	void findBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@DisplayName("이름 없이 구현체 타입으로만 조회")
	@Test
	void findBeanByType2() {
		MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
		
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
}

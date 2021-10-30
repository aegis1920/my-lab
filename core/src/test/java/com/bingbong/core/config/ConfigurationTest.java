package com.bingbong.core.config;

import com.bingbong.core.AppConfig;
import com.bingbong.core.AutoAppConfig;
import com.bingbong.core.member.MemberRepository;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;
import com.bingbong.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ConfigurationTest {
	@Test
	void name() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
		
		MemberService memberService = ac.getBean(MemberService.class);
		OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);
	}
}

package com.bingbong.core;

import com.bingbong.core.discount.DiscountPolicy;
import com.bingbong.core.discount.FixDiscountPolicy;
import com.bingbong.core.discount.RateDiscountPolicy;
import com.bingbong.core.member.MemberRepository;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;
import com.bingbong.core.member.MemoryMemberRepository;
import com.bingbong.core.order.OrderService;
import com.bingbong.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}
	
	@Bean
	public MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
	
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	@Bean
	public DiscountPolicy discountPolicy() {
		return new RateDiscountPolicy();
	}
	
	
}

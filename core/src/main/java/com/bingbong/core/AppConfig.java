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

public class AppConfig {
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}
	
	private MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
	
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	private DiscountPolicy discountPolicy() {
		return new RateDiscountPolicy();
	}
	
	
}

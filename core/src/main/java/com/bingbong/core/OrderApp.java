package com.bingbong.core;

import com.bingbong.core.member.Grade;
import com.bingbong.core.member.Member;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;
import com.bingbong.core.order.Order;
import com.bingbong.core.order.OrderService;
import com.bingbong.core.order.OrderServiceImpl;

public class OrderApp {
	public static void main(String[] args) {
		AppConfig appConfig = new AppConfig();
		MemberService memberService = appConfig.memberService();
		OrderService orderService = appConfig.orderService();
		
		Member memberA = new Member(1L, "memberA", Grade.VIP);
		memberService.join(memberA);
		
		Order order = orderService.createOrder(1L, "itemA", 10000);
	}
}

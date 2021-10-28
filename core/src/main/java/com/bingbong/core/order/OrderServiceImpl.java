package com.bingbong.core.order;

import com.bingbong.core.discount.DiscountPolicy;
import com.bingbong.core.discount.FixDiscountPolicy;
import com.bingbong.core.member.Member;
import com.bingbong.core.member.MemberRepository;
import com.bingbong.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
	
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	
	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
}

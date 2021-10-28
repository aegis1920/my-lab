package com.bingbong.core.discount;

import com.bingbong.core.member.Member;

public interface DiscountPolicy {
	
	int discount(Member member, int price);
}

package com.bingbong.core.autowired;

import com.bingbong.core.AutoAppConfig;
import com.bingbong.core.discount.DiscountPolicy;
import com.bingbong.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

class AllBeanTest {
	
	@Test
	void name() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
		
		DiscountService bean = ac.getBean(DiscountService.class);
	}
	
	static class DiscountService {
		private final Map<String, DiscountPolicy> policyMap;
		
		@Autowired
		DiscountService(Map<String, DiscountPolicy> policyMap) {
			this.policyMap = policyMap;
			System.out.println(policyMap);
		}
		
		public int discount(Member member, int price, String discountCode) {
			DiscountPolicy discountPolicy = policyMap.get(discountCode);
			return discountPolicy.discount(member, price);
		}
	}
	
}

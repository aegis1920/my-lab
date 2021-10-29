package com.bingbong.core.beanfind;

import com.bingbong.core.AppConfig;
import com.bingbong.core.discount.DiscountPolicy;
import com.bingbong.core.member.MemberRepository;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;
import com.bingbong.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationContextSameBeanFindTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
	
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면 중복 오류")
	@Test
	void findBeanByTypeDuplicate() {
		assertThatThrownBy(() -> ac.getBean(MemberRepository.class))
				.isInstanceOf(NoUniqueBeanDefinitionException.class);
	}
	
	@DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
	@Test
	void findBeanByTypeDuplicate2() {
		MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
		assertThat(memberRepository1).isInstanceOf(MemberRepository.class);
	}
	
	@DisplayName("특정 타입 모두 조회")
	@Test
	void findBeanByTypeDuplicate3() {
		Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
		
		assertThat(beansOfType).hasSize(2);
	}
	
	@Configuration
	static class SameBeanConfig {
	
		@Bean
		public MemberRepository memberRepository1() {
			return new MemoryMemberRepository();
		}
		
		@Bean
		public MemberRepository memberRepository2() {
			return new MemoryMemberRepository();
		}
	}
}

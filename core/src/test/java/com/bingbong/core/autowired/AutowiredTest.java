package com.bingbong.core.autowired;

import com.bingbong.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

class AutowiredTest {

	@Test
	void autowiredOption() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
	}
	
	static class TestBean {
		
		@Autowired(required = false)
		public void setNoBean1(Member nobean1) {
			System.out.println("nobean1 = " + nobean1);
		}
		
		@Autowired
		public void setNoBean2(@Nullable  Member nobean2) {
			System.out.println("nobean2 = " + nobean2);
		}
		
		@Autowired
		public void setNoBean3(Optional<Member> nobean3) {
			System.out.println("nobean3 = " + nobean3);
		}
	}
}

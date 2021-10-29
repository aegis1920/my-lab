package com.bingbong.core.beanfind;

import com.bingbong.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

class BeanDefinitionTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@DisplayName("직접 등록한 애플리케이션 빈 Definition 출력")
	@Test
	void findApplicationBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		Arrays.stream(beanDefinitionNames)
				.filter(it -> ac.getBeanDefinition(it).getRole() == BeanDefinition.ROLE_APPLICATION)
				.forEach(it -> System.out.println("name : " + it +" object : " + ac.getBeanDefinition(it)));
	}
}

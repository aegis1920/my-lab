package com.bingbong.event;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public class EventsResetProcessor {
	private ThreadLocal<Integer> nestedCount = new ThreadLocal<Integer>() {
		
		@Override
		protected Integer initialValue() {
			return new Integer(0);
		}
	};
	
	@Around("@Target(org.springframework.stereotype.Service) and within(com.myshop..*)")
	public Object doReset(ProceedingJoinPoint joinPoint) throws Throwable {
		nestedCount.set(nestedCount.get() + 1);
		try {
			return joinPoint.proceed();
		} finally {
			nestedCount.set(nestedCount.get() - 1);
			if (nestedCount.get() == 0) {
				Events.reset();
			}
		}
	}
}

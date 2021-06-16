package com.bingbong;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ReflectionTest {
	
	@Test
	void objectCreateAndMethodCall() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Duck duck = new Duck();
		duck.quack();
		
		Class<?> duckClass = Class.forName("com.bingbong.ReflectionTest$Duck"); // 클래스를 이름으로 찾아와서
		Object duckObject = duckClass.getDeclaredConstructor().newInstance(); // 기본생성자로 인스턴스를 만듦
		Method quack = duckObject.getClass().getDeclaredMethod("quack", new Class<?>[0]); // 해당 Method를 가지고 옴
		quack.invoke(duckObject); // duckObject를 통해서 quack 메서드를 실행
	}
	
	static class Duck {
		void quack() {
			System.out.println("꽥꽥!");
		}
	}
}

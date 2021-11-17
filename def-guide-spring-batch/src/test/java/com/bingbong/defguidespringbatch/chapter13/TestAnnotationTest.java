package com.bingbong.defguidespringbatch.chapter13;

import org.junit.jupiter.api.*;

class TestAnnotationTest {
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("-----------------Before All----------------");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("-----------------After All----------------");
	}
	
	@BeforeEach
	void setUp() {
		System.out.println("-----------------Before Each----------------");
	}
	
	@AfterEach
	void tearDown() {
		System.out.println("-----------------After Each----------------");
	}
	
	@DisplayName("띄어쓰기를 포함한 한글이 가능합니다.")
	@Test
	void test1() {
		System.out.println("Test1");
	}
	
	@Test
	void 테스트_띄어쓰기_힘들어() {
		System.out.println("Test2");
	}
	
	//	@Ignore("아직 메서드 구현은 힘듬") // Junit 4에서 @Ignore을 쓴다.
	@Disabled("아직 메서드 구현은 힘듬")
	@Test
	void test3() {
		System.out.println("Test3");
	}
}

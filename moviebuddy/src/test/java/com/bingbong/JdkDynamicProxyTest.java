package com.bingbong;

import com.bingbong.data.JpaMovieRepository;
import com.bingbong.domain.Movie;
import com.bingbong.domain.MovieDataRepository;
import com.bingbong.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JdkDynamicProxyTest {
	
	@Test
	void useDynamicProxy() {
		JpaMovieRepository movieRepository = new JpaMovieRepository();
		
		ClassLoader loader = JdkDynamicProxyTest.class.getClassLoader();
		Class<?>[] interfaces = new Class[] {MovieRepository.class};
		// Handler가 실행된다음에 invoke의 반환값이 들어온다.
		InvocationHandler handler = new MovieDataRepositoryHandler(movieRepository);
		
		// 프록시를 만들어서 실행
		MovieRepository proxy = (MovieRepository) Proxy.newProxyInstance(loader, interfaces, handler);// 3가지 인자를 받는다.
		
		Movie movie = new Movie("극한직업", "아무개");
		// save할 때 그대로 다시 나오기 때문에 같은 객체가 나온다.
		assertEquals(movie.getTitle(), proxy.save(movie).getTitle());
		assertEquals(movie.getDirector(), proxy.findByDirector(movie.getDirector()).getDirector());
		
		// 순수하게 프록시를 생성하는 기능만 담당
//		ProxyFactoryBean
		
	}
	
	static class MovieDataRepositoryHandler implements InvocationHandler {
		
		final MovieRepository movieRepository;
		
		MovieDataRepositoryHandler(MovieRepository movieRepository) {
			this.movieRepository = movieRepository;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String methodName = method.getName();
			
			// save라는 메서드 이름이라면 인자를 리턴해라.
			// Movie 객체를 바로 돌려줘라는 뜻
			if ("save".equals(methodName)) {
				return args[0];
			} else if ("findByDirector".equals(methodName)) {
				return new Movie("", String.valueOf(args[0]));
			}
			
			return null;
		}
	}
}

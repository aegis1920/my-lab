package com.bingbong;

import com.bingbong.domain.Movie;
import com.bingbong.domain.MovieRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Objects;

@Configuration
@ComponentScan
@EnableTransactionManagement
//@EnableJpaRepositories 얘를 선언하는 순간 Repository를 상속했던 얘들을 찾아서 Bean으로 등록한다.
public class MovieBuddyApplication {
	
	// JPA로 영화 정보를 저장하고 조회하는 애플리케이션
	public static void main(String[] args) {
		SpringApplication.run(MovieBuddyApplication.class, args);
	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	// 원래 ORM, XML 등 부가적인 정보를 많이 줘야하는데 해당 Bean을 이용하면 빠르게 만들 수 있다.
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		var vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.H2);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		
		var factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(getClass().getPackage().getName()); // 현재 패키지부터 쭉 찾음
		factory.setDataSource(dataSource());
		
		return factory;
	}
	
	// 1. ProxyFactoryBean을 이용해 MovieRepository 프록시를 빈으로 등록하려고 함
	// 2. 등록된 MovieRepository 프록시는 이미 잘 구현된 MyJpaRepository를 사용해서 동작하게 함
	// 순수하게 프록시를 생성하는 기능만 담당
	@Bean
	public ProxyFactoryBean movieRepository(EntityManagerFactory entityManagerFactory) {
		var sharedEntityManagerBean = new SharedEntityManagerBean();
		sharedEntityManagerBean.setEntityManagerFactory(entityManagerFactory);
		sharedEntityManagerBean.afterPropertiesSet(); // 초기화
		
		var entityManager = sharedEntityManagerBean.getObject();
		var myJpaRepository = new MyJpaRepository(entityManager);
		// 만약 이 방식으로 한다면 Bean이 소멸될 때 EntityManager도 같이 소멸된다. 이러면 제대로 쓸 수 없다.
		// 또한 스프링 Bean의 라이프 사이클에 맞지 않게 된다.
//		var entityManager = entityManagerFactory.createEntityManager();
		
		var proxyFactory = new ProxyFactoryBean();
		proxyFactory.setTarget(myJpaRepository);
		proxyFactory.setInterfaces(MovieRepository.class);
		proxyFactory.addAdvice((MethodInterceptor) invocation -> {
			
			// interface에 있는 save메서드를 실행했을 때 MyJpaRepository에 있는 save를 찾음
//			invocation.getThis() -> 위에서 입력받은 target(myJpaRepository)을 의미
//			invocation.getMethod() -> 호출된 프록시 메서드를 의미
//			invocation.getArguments() -> 호출된 프록시 메서드로 넘겨받은 매개변수
			
			Object target = invocation.getThis();
			Method method = ReflectionUtils.findMethod(target.getClass(), invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());
			
			// 실제로 프록시로 호출된 메소드의 정보로 target에 있는 메서드 정보를 찾아낸다.
			// 그리고 실행함
			// 그래서 프록시를 호출했지만 Target에 있는 메서드가 실행된다.
			// 동적으로 메서드를 만들어내서 동작. 실제로는 훨씬 복잡함.
			return method.invoke(target, invocation.getArguments());
		});
		return proxyFactory;
	}
	
	// MovieDataRepository는 프록시 객체를 만들어서 Bean으로 등록한다. SimpleJpaRepository 객체와 동적으로 쿼리를 생성해서 실행하는 전략을 가지고 한다.
	// 이걸 자동으로 해주는 게 @EnableJpaRepositories
//	@Bean
//	public JpaRepositoryFactoryBean<MovieDataRepository, Movie, Long> movieRepository(EntityManagerFactory entityManagerFactory) {
//		return new JpaRepositoryFactoryBean<>(MovieDataRepository.class);
//	}
	
	static class MyJpaRepository {
		private EntityManager entityManager;
		
		public MyJpaRepository(EntityManager entityManager) {
			this.entityManager = entityManager;
		}
		
		public Movie save(Movie entity) {
			if (Objects.nonNull(entity.getId())) {
				// ID가 있으면 이미 있다는 소리니까 갱신
				return entityManager.merge(entity);
			}
			
			// 저장
			entityManager.persist(entity);
			return entity;
		}
		
		public Movie findByDirector(String director) {
			String jpql = "select m from Movie m where m.director = :director";
			var query = entityManager.createQuery(jpql, Movie.class);
			query.setParameter("director", director);
			
			return query.getSingleResult();
		}
	}
	
}

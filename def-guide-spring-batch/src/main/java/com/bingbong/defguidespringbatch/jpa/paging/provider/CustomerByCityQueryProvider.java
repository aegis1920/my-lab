package com.bingbong.defguidespringbatch.jpa.paging.provider;

import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Query 객체를 사용할 수 있게 해주는 QueryProvider
 *
 * QueryProvider를 쉽게 구현할 수 있게 하는 AbstractJpaQueryProvider 추상 기본 클래스를 제공한다.
 */
public class CustomerByCityQueryProvider extends AbstractJpaQueryProvider {
	private String cityName;
	
	@Override
	public Query createQuery() {
		EntityManager manager = getEntityManager();
		Query query = manager.createQuery("select c from Customer c where c.city = :city");
		query.setParameter("city", cityName);
		
		return query;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cityName, "City name is required");
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}

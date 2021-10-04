package com.bingbong.defguidespringbatch.chapter7.hibernate.paging.config;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Component
public class HibernateBatchConfigurer extends DefaultBatchConfigurer {
	
	private DataSource dataSource;
	private SessionFactory sessionFactory;
	private PlatformTransactionManager transactionManager;
	
	public HibernateBatchConfigurer(DataSource dataSource, EntityManagerFactory entityManagerFactory) {
		super(dataSource); // 단순히 set하는 과정
		this.dataSource = dataSource;
		this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		this.transactionManager = new HibernateTransactionManager(this.sessionFactory);
	}
	
	@Override
	public PlatformTransactionManager getTransactionManager() {
		return this.transactionManager;
	}
}

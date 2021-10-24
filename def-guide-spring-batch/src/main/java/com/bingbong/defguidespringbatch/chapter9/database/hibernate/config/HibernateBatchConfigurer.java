package com.bingbong.defguidespringbatch.chapter9.database.hibernate.config;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Component
public class HibernateBatchConfigurer implements BatchConfigurer {
	
	private DataSource dataSource;
	private SessionFactory sessionFactory;
	private PlatformTransactionManager transactionManager;
	private JobRepository jobRepository;
	private JobExplorer jobExplorer;
	private JobLauncher jobLauncher;
	
	public HibernateBatchConfigurer(DataSource dataSource, EntityManagerFactory entityManagerFactory) {
		this.dataSource = dataSource;
		this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	@PostConstruct
	public void initialize() {
		try {
			System.out.println("Hello : " + sessionFactory.getCurrentSession());
			HibernateTransactionManager transactionManager = new HibernateTransactionManager(this.sessionFactory);
			transactionManager.afterPropertiesSet();
			this.transactionManager = transactionManager;
			this.jobRepository = createJobRepository();
			this.jobExplorer = createJobExplorer();
			this.jobLauncher = createJobLauncher();
		} catch (Exception e) {
			throw new BatchConfigurationException(e);
		}
	}
	
	private JobLauncher createJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(this.jobRepository);
		jobLauncher.afterPropertiesSet();
		
		return jobLauncher;
	}
	
	private JobExplorer createJobExplorer() throws Exception {
		JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
		jobExplorerFactoryBean.setDataSource(this.dataSource);
		jobExplorerFactoryBean.afterPropertiesSet();
		
		return jobExplorerFactoryBean.getObject();
	}
	
	private JobRepository createJobRepository() throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(this.dataSource);
		jobRepositoryFactoryBean.setTransactionManager(this.transactionManager);
		jobRepositoryFactoryBean.afterPropertiesSet();
		
		return jobRepositoryFactoryBean.getObject();
	}
	
	@Override
	public PlatformTransactionManager getTransactionManager() {
		return this.transactionManager;
	}
	
	@Override
	public JobRepository getJobRepository() throws Exception {
		return jobRepository;
	}
	
	@Override
	public JobLauncher getJobLauncher() throws Exception {
		return jobLauncher;
	}
	
	@Override
	public JobExplorer getJobExplorer() throws Exception {
		return jobExplorer;
	}
}

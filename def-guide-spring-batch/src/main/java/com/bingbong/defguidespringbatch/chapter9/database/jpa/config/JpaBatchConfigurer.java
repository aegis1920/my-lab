package com.bingbong.defguidespringbatch.chapter9.database.jpa.config;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Component
public class JpaBatchConfigurer implements BatchConfigurer {
	
	private DataSource dataSource;
	private EntityManagerFactory entityManagerFactory;
	private PlatformTransactionManager transactionManager;
	private JobRepository jobRepository;
	private JobExplorer jobExplorer;
	private JobLauncher jobLauncher;
	
	public JpaBatchConfigurer(DataSource dataSource, EntityManagerFactory entityManagerFactory) {
		this.dataSource = dataSource;
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@PostConstruct
	public void initialize() {
		try {
			JpaTransactionManager transactionManager = new JpaTransactionManager(this.entityManagerFactory);
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

package com.bingbong.defguidespringbatch.chapter7.hibernate.paging;

import com.bingbong.defguidespringbatch.chapter7.hibernate.paging.domain.Customer;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernatePagingItemReader;
import org.springframework.batch.item.database.builder.HibernatePagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@EnableBatchProcessing
@SpringBootApplication
public class HibernatePagingApplication {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Bean
	public Job customerHibernatePagingJob() {
		return this.jobBuilderFactory.get("customerHibernatePagingJob")
				.start(customerHibernatePagingStep())
				.build();
	}
	
	@Bean
	public Step customerHibernatePagingStep() {
		return this.stepBuilderFactory.get("customerHibernatePagingStep")
				.<Customer, Customer>chunk(2)
				.reader(customerHibernatePagingItemReader(entityManagerFactory, null))
				.writer(customerHibernatePagingItemWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public HibernatePagingItemReader<Customer> customerHibernatePagingItemReader(EntityManagerFactory entityManagerFactory, @Value("#{jobParameters['city']}") String city) {
		return new HibernatePagingItemReaderBuilder<Customer>()
				.name("customerHibernatePagingItemReader")
				.sessionFactory(entityManagerFactory.unwrap(SessionFactory.class))
				.queryString("from Customer where city = :city") // table명이 아니라 Entity명으로 써줘야됨 (대소문자 구별)
				.parameterValues(Collections.singletonMap("city", city))
				.pageSize(10)
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerHibernatePagingItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HibernatePagingApplication.class, "--job.name=customerHibernatePagingJob", "city=Tuscaloosa");
	}
}

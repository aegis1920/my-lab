package com.bingbong.defguidespringbatch.hibernate.cursor;

import com.bingbong.defguidespringbatch.hibernate.cursor.domain.Customer;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@EnableBatchProcessing
@SpringBootApplication
public class HibernateCursorApplication {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Bean
	public Job customerHibernateCursorJob() {
		return this.jobBuilderFactory.get("customerHibernateCursorJob")
				.start(customerHibernateCursorStep())
				.build();
	}
	
	@Bean
	public Step customerHibernateCursorStep() {
		return this.stepBuilderFactory.get("customerHibernateCursorStep")
				.<Customer, Customer>chunk(2)
				.reader(customerHibernateCursorItemReader(entityManagerFactory, null))
				.writer(customerHibernateCursorItemWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public HibernateCursorItemReader<Customer> customerHibernateCursorItemReader(EntityManagerFactory entityManagerFactory, @Value("#{jobParameters['city']}") String city) {
		return new HibernateCursorItemReaderBuilder<Customer>()
				.name("customerHibernateCursorItemReader")
				.sessionFactory(entityManagerFactory.unwrap(SessionFactory.class))
				.queryString("from Customer where city = :city") // table명이 아니라 Entity명으로 써줘야됨 (대소문자 구별)
				.parameterValues(Collections.singletonMap("city", city))
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerHibernateCursorItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HibernateCursorApplication.class, "--job.name=customerHibernateCursorJob", "city=Gary");
	}
}

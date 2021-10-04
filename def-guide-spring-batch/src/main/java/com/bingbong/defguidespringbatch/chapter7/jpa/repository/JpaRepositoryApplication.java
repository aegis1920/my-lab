package com.bingbong.defguidespringbatch.chapter7.jpa.repository;

import com.bingbong.defguidespringbatch.chapter7.jpa.repository.domain.Customer;
import com.bingbong.defguidespringbatch.chapter7.jpa.repository.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@EnableBatchProcessing
@SpringBootApplication
public class JpaRepositoryApplication {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Bean
	public Job customerJpaRepositoryJob() {
		return this.jobBuilderFactory.get("customerJpaRepositoryJob")
				.start(customerJpaRepositoryStep())
				.build();
	}
	
	@Bean
	public Step customerJpaRepositoryStep() {
		return this.stepBuilderFactory.get("customerJpaRepositoryStep")
				.<Customer, Customer>chunk(2)
				.reader(customerJpaRepositoryItemReader(null))
				.writer(customerJpaRepositoryItemWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public RepositoryItemReader<Customer> customerJpaRepositoryItemReader(@Value("#{jobParameters['city']}") String city) {
		return new RepositoryItemReaderBuilder<Customer>()
				.name("customerJpaRepositoryItemReader")
				.arguments(Collections.singletonList(city))
				.methodName("findByCity")
				.repository(customerRepository)
				.sorts(Collections.singletonMap("lastName", Sort.Direction.ASC))
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerJpaRepositoryItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JpaRepositoryApplication.class, "--job.name=customerJpaRepositoryJob", "city=Sandy");
	}
}

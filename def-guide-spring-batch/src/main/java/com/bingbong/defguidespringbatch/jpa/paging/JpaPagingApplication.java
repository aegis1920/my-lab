package com.bingbong.defguidespringbatch.jpa.paging;

import com.bingbong.defguidespringbatch.jpa.paging.domain.Customer;
import com.bingbong.defguidespringbatch.jpa.paging.provider.CustomerByCityQueryProvider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@EnableBatchProcessing
@SpringBootApplication
public class JpaPagingApplication {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Bean
	public Job customerJpaPagingJob() {
		return this.jobBuilderFactory.get("customerJpaPagingJob")
				.start(customerJpaPagingStep())
				.build();
	}
	
	@Bean
	public Step customerJpaPagingStep() {
		return this.stepBuilderFactory.get("customerJpaPagingStep")
				.<Customer, Customer>chunk(2)
				.reader(customerJpaPagingItemReader(entityManagerFactory, null))
				.writer(customerJpaPagingItemWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public JpaPagingItemReader<Customer> customerJpaPagingItemReader(EntityManagerFactory entityManagerFactory, @Value("#{jobParameters['city']}") String city) {
		CustomerByCityQueryProvider queryProvider = new CustomerByCityQueryProvider();
		
		return new JpaPagingItemReaderBuilder<Customer>()
				.name("customerJpaPagingItemReader")
				.entityManagerFactory(entityManagerFactory)
				.queryProvider(queryProvider)
				.parameterValues(Collections.singletonMap("city", city))
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerJpaPagingItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JpaPagingApplication.class, "--job.name=customerJpaPagingJob", "city=Davenport");
	}
}

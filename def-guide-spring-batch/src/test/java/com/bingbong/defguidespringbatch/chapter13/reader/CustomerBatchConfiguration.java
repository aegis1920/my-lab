package com.bingbong.defguidespringbatch.chapter13.reader;

import com.bingbong.defguidespringbatch.chapter13.domain.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class CustomerBatchConfiguration {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
	}
	
	@Autowired
	public void setStepBuilderFactory(StepBuilderFactory stepBuilderFactory) {
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean
	public CustomerItemReader customerItemReader() {
		return new CustomerItemReader();
	}
	
	@Bean
	public ItemWriter<Customer> itemWriter() {
		return (list -> list.forEach(System.out::println));
	}
	
	@Bean
	public Step step1234() {
		return this.stepBuilderFactory.get("step1234")
				.<Customer, Customer>chunk(10)
				.reader(customerItemReader())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job job1234() {
		return this.jobBuilderFactory.get("job1234")
				.start(step1234())
				.build();
	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.build();
	}
}


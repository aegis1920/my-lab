package com.bingbong.defguidespringbatch.chapter9.database.jpa;

import com.bingbong.defguidespringbatch.chapter9.database.jpa.domain.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing
@SpringBootApplication
public class JpaItemWriterJobApplication {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public JpaItemWriterJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean
	public Job jpaItemWriterJob() {
		return this.jobBuilderFactory
				.get("jpaItemWriterJob")
				.start(jpaItemWriterStep())
				.build();
	}
	
	@Bean
	public Step jpaItemWriterStep() {
		return this.stepBuilderFactory.get("jpaItemWriterStep")
				.<Customer, Customer>chunk(2)
				.reader(customerFlatFileItemReader(null))
				.writer(jpaItemWriter(null))
				.build();
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<Customer> customerFlatFileItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {
		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerFlatFileItemReader")
				.resource(inputFile)
				.delimited()
				.names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip")
				.targetType(Customer.class)
				.build();
	}
	
	@Bean
	public JpaItemWriter<Customer> jpaItemWriter(EntityManagerFactory entityManagerFactory) {
		return new JpaItemWriterBuilder<Customer>()
				.entityManagerFactory(entityManagerFactory)
				.build();
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=jpaItemWriterJob");
		realArgs.add("customerFile=input/chapter9_customer.csv"); // project의 resource에 생성된다.
		
		SpringApplication.run(JpaItemWriterJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

package com.bingbong.defguidespringbatch.chapter9.database.hibernate;

import com.bingbong.defguidespringbatch.chapter9.database.hibernate.domain.Customer;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.batch.item.database.builder.HibernateItemWriterBuilder;
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
public class HibernateItemWriterJobApplication {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public HibernateItemWriterJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean
	public Job hibernateItemWriterJob() {
		return this.jobBuilderFactory
				.get("hibernateItemWriterJob")
				.start(hibernateItemWriterStep())
				.build();
	}
	
	@Bean
	public Step hibernateItemWriterStep() {
		return this.stepBuilderFactory.get("hibernateItemWriterStep")
				.<Customer, Customer>chunk(2)
				.reader(customerFlatFileItemReader(null))
				.writer(hibernateItemWriter(null))
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
	public HibernateItemWriter<Customer> hibernateItemWriter(EntityManagerFactory entityManager) {
		return new HibernateItemWriterBuilder<Customer>()
				.sessionFactory(entityManager.unwrap(SessionFactory.class))
				.build();
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=hibernateItemWriterJob");
		realArgs.add("customerFile=input/chapter9_customer.csv"); // project의 resource에 생성된다.
		
		SpringApplication.run(HibernateItemWriterJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

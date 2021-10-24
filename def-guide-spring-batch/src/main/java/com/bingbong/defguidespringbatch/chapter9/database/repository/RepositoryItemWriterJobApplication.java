package com.bingbong.defguidespringbatch.chapter9.database.repository;

import com.bingbong.defguidespringbatch.chapter9.database.repository.domain.Customer;
import com.bingbong.defguidespringbatch.chapter9.database.repository.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing
@SpringBootApplication
// 따로 @Configuration에 만들경우, 아래 @EnableJpaRepositories을 추가해줘야 한다.
// @EnableJpaRepositories(basePackageClasses = Customer.class)
public class RepositoryItemWriterJobApplication {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CustomerRepository customerRepository;
	
	public RepositoryItemWriterJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CustomerRepository customerRepository) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.customerRepository = customerRepository;
	}
	
	@Bean
	public Job repositoryItemWriterJob() {
		return this.jobBuilderFactory
				.get("repositoryItemWriterJob")
				.start(repositoryItemWriterStep())
				.build();
	}
	
	@Bean
	public Step repositoryItemWriterStep() {
		return this.stepBuilderFactory.get("repositoryItemWriterStep")
				.<Customer, Customer>chunk(2)
				.reader(customerFlatFileItemReader(null))
				.writer(repositoryItemWriter(null))
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
	public RepositoryItemWriter<Customer> repositoryItemWriter(CustomerRepository repository) {
		return new RepositoryItemWriterBuilder<Customer>()
				.repository(customerRepository)
				.methodName("save")
				.build();
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=repositoryItemWriterJob");
		realArgs.add("customerFile=input/chapter9_customer.csv"); // project의 resource에 생성된다.
		
		SpringApplication.run(RepositoryItemWriterJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

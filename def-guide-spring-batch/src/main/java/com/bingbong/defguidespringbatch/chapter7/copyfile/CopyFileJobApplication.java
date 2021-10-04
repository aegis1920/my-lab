package com.bingbong.defguidespringbatch.chapter7.copyfile;

import com.bingbong.defguidespringbatch.chapter6.transactionjob.domain.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.text.SimpleDateFormat;

@EnableBatchProcessing
@SpringBootApplication
public class CopyFileJobApplication {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public CopyFileJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean
	public Job copyFileJob() {
		return this.jobBuilderFactory
				.get("copyFileJob")
				.start(copyFileStep())
				.build();
	}
	
	@Bean
	public Step copyFileStep() {
		return this.stepBuilderFactory.get("copyFileStep")
				.<Customer, Customer>chunk(10)
//				.reader(customerItemReader(null))
				.reader(customerJsonItemReader(null))
				.writer(itemWriter())
				.build();
	}
	
	// delimiter로 이뤄진 파일을 읽음. 기본값은 콤마. 즉, csv로 읽음
	@Bean
	@StepScope
	public FlatFileItemReader<Customer> customerFlatFileItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {
		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerFlatFileItemReader")
				.delimited()
				.names("firstName", "middleInitial", "lastName", "addressNumber", "street", "city", "state", "zipCode")
				.targetType(Customer.class)
				.resource(inputFile)
				.build();
	}
	
	// json 파일을 읽음
	@Bean
	@StepScope
	public JsonItemReader<Customer> customerJsonItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		
		JacksonJsonObjectReader<Customer> jsonObjectReader = new JacksonJsonObjectReader<>(Customer.class);
		jsonObjectReader.setMapper(objectMapper);
		
		return new JsonItemReaderBuilder<Customer>()
				.name("customerJsonItemReader")
				.jsonObjectReader(jsonObjectReader)
				.resource(inputFile)
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> itemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CopyFileJobApplication.class, args);
	}
}

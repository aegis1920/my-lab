package com.bingbong.defguidespringbatch.chapter9.database.jdbc;

import com.bingbong.defguidespringbatch.chapter9.database.jdbc.domain.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SQL의 customer 스키마가 다르다. 경로도 예제와 조금씩 다름.
 */
@EnableBatchProcessing
@SpringBootApplication
public class JdbcItemWriterJobApplication {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public JdbcItemWriterJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Bean
	public Job jdbcItemWriterJob() {
		return this.jobBuilderFactory
				.get("jdbcItemWriterJob")
				.start(jdbcItemWriterStep())
				.build();
	}
	
	@Bean
	public Step jdbcItemWriterStep() {
		return this.stepBuilderFactory.get("jdbcItemWriterStep")
				.<Customer, Customer>chunk(2)
				.reader(customerFlatFileItemReader(null))
				.writer(jdbcItemWriter(null))
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
	@StepScope
	public JdbcBatchItemWriter<Customer> jdbcItemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Customer>()
				.dataSource(dataSource)
				// ItemPreparedStatementSetter를 사용했을 때의 방법
//				.sql("INSERT INTO chapter9_customer (first_name, middle_initial, last_name, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?)")
				.sql("INSERT INTO chapter9_customer (first_name, middle_initial, last_name, address, city, state, zip) VALUES (:firstName, :middleInitial, :lastName, :address, :city, :state, :zip)")
				// ItemPreparedStatementSetter 인터페이스를 구현하는 방법. 물음표(?)를 사용한다.
//				.itemPreparedStatementSetter(new CustomerItemPreparedStatementSetter())
				// itemPreparedStatementSetter보다 선호되는 방법. BeanPropertyItemSqlParameterSourceBuilder를 사용해서
				// SQL에 사용할 Item 값을 추출한다.
				.beanMapped()
				.build();
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=jdbcItemWriterJob");
		realArgs.add("customerFile=input/chapter9_customer.csv"); // project의 resource에 생성된다.
		
		SpringApplication.run(JdbcItemWriterJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

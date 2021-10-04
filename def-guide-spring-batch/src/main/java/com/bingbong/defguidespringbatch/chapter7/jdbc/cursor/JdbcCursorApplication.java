package com.bingbong.defguidespringbatch.chapter7.jdbc.cursor;

import com.bingbong.defguidespringbatch.chapter7.jdbc.cursor.domain.Customer;
import com.bingbong.defguidespringbatch.chapter7.jdbc.cursor.mapper.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.sql.DataSource;

@EnableBatchProcessing
@SpringBootApplication
public class JdbcCursorApplication {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job customerJdbcCursorJob() {
		return this.jobBuilderFactory.get("customerJdbcCursorJob")
				.start(customerJdbcCursorStep())
				.build();
	}
	
	@Bean
	public Step customerJdbcCursorStep() {
		return this.stepBuilderFactory.get("customerJdbcCursorStep")
				.<Customer, Customer>chunk(2)
				.reader(customerJdbcCursorItemReader(null))
				.writer(customerJdbcCursorItemWriter())
				.build();
	}
	
	@Bean
	public JdbcCursorItemReader<Customer> customerJdbcCursorItemReader(DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Customer>()
				.name("customerJdbcCursorItemReader")
				.dataSource(dataSource)
				.sql("select * from customer where city = ?")
				.rowMapper(new CustomerRowMapper())
				.preparedStatementSetter(citySetter(null))
				.build();
	}
	
	@Bean
	@StepScope
	public PreparedStatementSetter citySetter(@Value("#{jobParameters['city']}") String city) {
		return new ArgumentPreparedStatementSetter(new Object[] {city});
	}
	
	@Bean
	public ItemWriter<Customer> customerJdbcCursorItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JdbcCursorApplication.class, "--job.name=customerJdbcCursorJob", "city=Stamford");
	}
}

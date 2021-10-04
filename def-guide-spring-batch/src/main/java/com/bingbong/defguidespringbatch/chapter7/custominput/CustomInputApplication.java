package com.bingbong.defguidespringbatch.chapter7.custominput;

import com.bingbong.defguidespringbatch.chapter7.custominput.customreader.CustomerItemReader;
import com.bingbong.defguidespringbatch.chapter7.custominput.domain.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * 50번째에 throw를 하기 때문에 실패하고
 * Job을 재시작하면 종료된 지점에서 다시 시작하게 됨.
 * 다만 현재 로직은 인메모리로 실행하고 있으므로 재시작이 불가함.
 */
@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CustomInputApplication extends DefaultBatchConfigurer {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public CustomerItemReader customerCustomInputItemReader() {
		CustomerItemReader customerItemReader = new CustomerItemReader();
		customerItemReader.setName("customerCustomInputItemReader");
		
		return customerItemReader;
	}
	
	@Bean
	public Job customerCustomInputJob() {
		return this.jobBuilderFactory.get("customerCustomInputJob")
				.start(customerCustomInputStep())
				.build();
	}
	
	@Bean
	public Step customerCustomInputStep() {
		return this.stepBuilderFactory.get("customerCustomInputStep")
				.<Customer, Customer>chunk(2)
				.reader(customerCustomInputItemReader())
				.writer(customerCustomInputItemWriter())
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerCustomInputItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomInputApplication.class, "--job.name=customerCustomInputJob");
	}
}

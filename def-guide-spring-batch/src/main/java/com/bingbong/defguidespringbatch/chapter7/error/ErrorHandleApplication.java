package com.bingbong.defguidespringbatch.chapter7.error;

import com.bingbong.defguidespringbatch.chapter7.error.domain.Customer;
import com.bingbong.defguidespringbatch.chapter7.error.listener.CustomerItemListener;
import com.bingbong.defguidespringbatch.chapter7.error.listener.EmptyInputStepFailer;
import com.bingbong.defguidespringbatch.chapter7.error.service.CustomerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ErrorHandleApplication extends DefaultBatchConfigurer {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public ItemReaderAdapter<Customer> customerErrorHandleItemReader(CustomerService customerService) {
		ItemReaderAdapter<Customer> adapter = new ItemReaderAdapter<>();
		
		adapter.setTargetObject(customerService);
		adapter.setTargetMethod("getCustomer");
		
		return adapter;
	}
	
	@Bean
	public Job customerErrorHandleJob() {
		return this.jobBuilderFactory.get("customerErrorHandleJob")
				.start(customerErrorHandleStep())
				.build();
	}
	
	@Bean
	public CustomerItemListener customerItemListener() {
		return new CustomerItemListener();
	}
	
	@Bean
	public EmptyInputStepFailer emptyInputStepFailer() {
		return new EmptyInputStepFailer();
	}
	
	@Bean
	public Step customerErrorHandleStep() {
		return this.stepBuilderFactory.get("customerErrorHandleStep")
				.<Customer, Customer>chunk(2)
				.reader(customerErrorHandleItemReader(customerService))
				.writer(customerErrorHandleItemWriter())
				.faultTolerant()
				// ParseExecption을 제외한 모든 예외를 10회까지 건너뜀
				.skip(Exception.class)
				.noSkip(ParseException.class)
				.skipLimit(10)
				// ParseExecption을 10회까지 건너뜀
//				.skip(ParseException.class)
//				.skipLimit(10)
				// Skip Policy로 지정해줄수도 있다
//				.skipPolicy(new FileVerificationSkipper())
				// Listener를 통해 더 자세한 로그를 남겨줄 수도 있다
//				.listener(customerItemListener())
				// Listener를 통해 빈 값이 들어오면 FAILED가 들어오도록 할 수 있다.
//				.listener(emptyInputStepFailer())
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerErrorHandleItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ErrorHandleApplication.class, "--job.name=customerErrorHandleJob");
	}
}

package com.bingbong.defguidespringbatch.chapter8.copyfile;

import com.bingbong.defguidespringbatch.chapter8.copyfile.domain.Customer;
import com.bingbong.defguidespringbatch.chapter8.copyfile.validator.UniqueLastNameValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * chpater8_customer.csv 파일에서 LastName이 동일하면 실패하기 때문에 6번째, 13번째 줄에 Darrow로 동일해서 예외가 발생한다.
 * 그래서 해당 Job을 통과시키려면 6번째, 13번째 줄을 삭제해야 한다.
 */
@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ValidationProcessJobApplication extends DefaultBatchConfigurer {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public ValidationProcessJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public Job validationProcessJob() {
		return this.jobBuilderFactory
				.get("validationProcessJob")
				.start(validationProcessStep())
				.build();
	}
	
	@Bean
	public Step validationProcessStep() {
		return this.stepBuilderFactory.get("validationProcessStep")
				.<Customer, Customer>chunk(5)
				.reader(customerFlatFileItemReader(null))
//				.processor(customerBeanValidatingItemProcessor())
				.processor(customerValidatingItemProcessor())
				.writer(itemWriter())
				.stream(validator())
				.build();
	}
	
	// delimiter로 이뤄진 파일을 읽음. 기본값은 콤마. 즉, csv로 읽음
	@Bean
	@StepScope
	public FlatFileItemReader<Customer> customerFlatFileItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {
		FlatFileItemReader<Customer> build = new FlatFileItemReaderBuilder<Customer>()
				.name("customerFlatFileItemReader")
				.delimited()
				.names("firstName", "middleInitial", "lastName", "address", "city", "state", "zipCode")
				.targetType(Customer.class)
				.resource(inputFile)
				.build();
		return build;
	}
	
	@Bean
	public ItemWriter<Customer> itemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	/**
	 * {@link BeanValidatingItemProcessor}은 {@link ValidatingItemProcessor}을 상속한 녀석
	 */
//	@Bean
//	public BeanValidatingItemProcessor<Customer> customerBeanValidatingItemProcessor() {
//		return new BeanValidatingItemProcessor<>();
//	}
	
	@Bean
	public UniqueLastNameValidator validator() {
		UniqueLastNameValidator uniqueLastNameValidator = new UniqueLastNameValidator();
		uniqueLastNameValidator.setName("validator");
		
		return uniqueLastNameValidator;
	}
	
	@Bean
	public ValidatingItemProcessor<Customer> customerValidatingItemProcessor() {
		return new ValidatingItemProcessor<>(validator());
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=validationProcessJob");
		realArgs.add("customerFile=input/chpater8_customer.csv");
		
		SpringApplication.run(ValidationProcessJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

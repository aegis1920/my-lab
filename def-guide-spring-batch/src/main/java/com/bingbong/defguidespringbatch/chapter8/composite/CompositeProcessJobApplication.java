package com.bingbong.defguidespringbatch.chapter8.composite;

import com.bingbong.defguidespringbatch.chapter8.composite.classifier.ZipCodeClassifier;
import com.bingbong.defguidespringbatch.chapter8.composite.domain.Customer;
import com.bingbong.defguidespringbatch.chapter8.composite.service.LowerCaseNameService;
import com.bingbong.defguidespringbatch.chapter8.composite.service.UpperCaseNameService;
import com.bingbong.defguidespringbatch.chapter8.composite.validator.UniqueLastNameValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.classify.Classifier;
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
public class CompositeProcessJobApplication extends DefaultBatchConfigurer {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public CompositeProcessJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public Job CompositeProcessJob() {
		return this.jobBuilderFactory
				.get("CompositeProcessJob")
				.start(CompositeProcessStep())
				.build();
	}
	
	@Bean
	public Step CompositeProcessStep() {
		return this.stepBuilderFactory.get("CompositeProcessStep")
				.<Customer, Customer>chunk(5)
				.reader(customerFlatFileItemReader(null))
//				.processor(itemProcessor()) // Composite
				.processor(classifierItemProcessor()) // classifier로 분류
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
				.names("firstName", "middleInitial", "lastName", "address", "city", "state", "zipCode")
				.targetType(Customer.class)
				.resource(inputFile)
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> itemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	@Bean
	public UniqueLastNameValidator validator() {
		UniqueLastNameValidator uniqueLastNameValidator = new UniqueLastNameValidator();
		uniqueLastNameValidator.setName("validator");
		
		return uniqueLastNameValidator;
	}

	@Bean
	public ItemProcessorAdapter<Customer, Customer> lowerCaseItemProcessor(LowerCaseNameService service) {
		ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();

		adapter.setTargetObject(service);
		adapter.setTargetMethod("lowerCase");

		return adapter;
	}

	@Bean
	public ItemProcessorAdapter<Customer, Customer> upperCaseItemProcessor(UpperCaseNameService service) {
		ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();

		adapter.setTargetObject(service);
		adapter.setTargetMethod("upperCase");

		return adapter;
	}
	
	@Bean
	public CompositeItemProcessor<Customer, Customer> itemProcessor() {
		CompositeItemProcessor<Customer, Customer> itemProcessor = new CompositeItemProcessor<>();
		
		itemProcessor.setDelegates(Arrays.asList(
				customerValidatingItemProcessor(),
				lowerCaseItemProcessor(null),
				upperCaseItemProcessor(null)
				)
		);
		
		return itemProcessor;
	}
	
	@Bean
	public ValidatingItemProcessor<Customer> customerValidatingItemProcessor() {
		ValidatingItemProcessor<Customer> itemProcessor = new ValidatingItemProcessor<>(validator());

		// 유효성 검증을 필터로 바꾼다. 
		itemProcessor.setFilter(true);
		
		return itemProcessor;
	}

	@Bean
	public Classifier classifier() {
		return new ZipCodeClassifier(upperCaseItemProcessor(null), lowerCaseItemProcessor(null));
	}

	@Bean
	public ClassifierCompositeItemProcessor<Customer, Customer> classifierItemProcessor() {
		ClassifierCompositeItemProcessor<Customer, Customer> itemProcessor = new ClassifierCompositeItemProcessor<>();

		itemProcessor.setClassifier(classifier());

		return itemProcessor;
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=CompositeProcessJob");
		realArgs.add("customerFile=input/chpater8_customer.csv");
		
		SpringApplication.run(CompositeProcessJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

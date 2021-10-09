package com.bingbong.defguidespringbatch.chapter8.adapter;

import com.bingbong.defguidespringbatch.chapter8.adapter.domain.Customer;
import com.bingbong.defguidespringbatch.chapter8.adapter.service.UpperCaseNameService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
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

@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AdapterProcessJobApplication extends DefaultBatchConfigurer {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	public AdapterProcessJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
	}

	@Bean
	public Job AdapterProcessJob() {
		return this.jobBuilderFactory
				.get("AdapterProcessJob")
				.start(AdapterProcessStep())
				.build();
	}

	@Bean
	public Step AdapterProcessStep() {
		return this.stepBuilderFactory.get("AdapterProcessStep")
				.<Customer, Customer>chunk(5)
				.reader(customerFlatFileItemReader(null))
				.processor(itemProcessor(null))
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
	public ItemProcessorAdapter<Customer, Customer> itemProcessor(UpperCaseNameService service) {
		ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();

		adapter.setTargetObject(service);
		adapter.setTargetMethod("upperCase");

		return adapter;
	}

	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));

		realArgs.add("--job.name=AdapterProcessJob");
		realArgs.add("customerFile=input/chpater8_customer.csv");

		SpringApplication.run(AdapterProcessJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

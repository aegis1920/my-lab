package com.bingbong.defguidespringbatch.chaptersbefore6.my.helloworld;

import com.bingbong.defguidespringbatch.chaptersbefore6.my.helloworld.customreader.CustomerItemReader;
import com.bingbong.defguidespringbatch.chaptersbefore6.my.helloworld.domain.Customer;
import com.bingbong.defguidespringbatch.chaptersbefore6.my.helloworld.listener.CustomerItemListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HelloWorldJobApplication extends DefaultBatchConfigurer {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public Job helloWorldJob() {

		return this.jobBuilderFactory.get("helloWorldJob")
				.start(helloWorldStep1())
				.next(helloWorldStep2())
				.build();
	}

	@Bean
	public Step helloWorldStep1() {
		return this.stepBuilderFactory.get("helloWorldStep1")
				.<Customer, Customer>chunk(2)
				.reader(customerHelloWorldItemReader())
				.writer(customerHelloWorldItemWriter())
				.listener(new CustomerItemListener())
				.build();
	}
	
	@Bean
	public Step helloWorldStep2() {
		return this.stepBuilderFactory.get("helloWorldStep2")
				.tasklet(helloWorldTasklet2(null))
				.build();
	}
	
	@Bean
	public CustomerItemReader customerHelloWorldItemReader() {
		CustomerItemReader customerItemReader = new CustomerItemReader();
		customerItemReader.setName("customerHelloWorldItemReader");
		
		return customerItemReader;
	}
	
	@Bean
	public ItemWriter<Customer> customerHelloWorldItemWriter() {
		return items -> items.forEach(System.out::println);
	}

	@StepScope
	@Bean
	public Tasklet helloWorldTasklet1(@Value("#{jobParameters['name']}") String name) {
		return (contribution, chunkContext) -> {
				System.out.printf("[Tasklet2] ---1111---, %s!%n", name);
				return RepeatStatus.FINISHED;
			};
	}
	
	@StepScope
	@Bean
	public Tasklet helloWorldTasklet2(@Value("#{jobParameters['name']}") String name) {
		return (contribution, chunkContext) -> {
			System.out.printf("[Tasklet2] ---2222---, %s!%n", name);
			return RepeatStatus.FINISHED;
		};
	}

	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=helloWorldJob");
		realArgs.add("name=helloworld");
		
		SpringApplication.run(HelloWorldJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

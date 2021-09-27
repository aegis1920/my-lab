package com.bingbong.defguidespringbatch.jobs;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class ExecutionContextJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

//	@Bean
//	public Job helloWorldBatchJob() {
//		return this.jobBuilderFactory.get("helloWorldBatchJob")
//				.start(helloWorldStep())
//				.build();
//	}
//
//	@Bean
//	public Step helloWorldStep() {
//		return this.stepBuilderFactory.get("helloWorldStep")
//				.tasklet(tasklet())
//				.build();
//	}

//	@StepScope
//	@Bean
//	public HelloWorldTasklet tasklet() {
//		return new HelloWorldTasklet();
//	}

//	public static void main(String[] args) {
//		SpringApplication.run(ExecutionContextJob.class, args);
//	}
}

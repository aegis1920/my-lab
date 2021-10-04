package com.bingbong.defguidespringbatch.chaptersbefore6.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@EnableBatchProcessing
@SpringBootApplication
public class NoRunJobApplication {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job noRunJob() {
		return this.jobBuilderFactory
				.get("noRunJob")
				.start(noRunStep())
				.build();
	}
	
	@Bean
	public Step noRunStep() {
		return this.stepBuilderFactory.get("noRunStep")
				.tasklet(((contribution, chunkContext) -> {
					System.out.println("현재 이 Job 또한 실행되지 않음!!!");
					return RepeatStatus.FINISHED;
				}))
				.build();
	}
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NoRunJobApplication.class);
		Properties properties = new Properties();
		// 모든 Job이 실행되지 않음
		properties.put("spring.batch.job.enabled", false);
		application.setDefaultProperties(properties);
		SpringApplication.run(HelloWorldJob.class, args);
	}
}



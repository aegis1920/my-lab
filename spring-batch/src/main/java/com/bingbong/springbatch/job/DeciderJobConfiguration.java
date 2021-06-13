package com.bingbong.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeciderJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job deciderJob() {
		return jobBuilderFactory.get("deciderJob")
				.start(startStep())
				.next(oddEvenDecider())
				.from(oddEvenDecider())
					.on("ODD")
					.to(oddStep())
				.from(oddEvenDecider())
					.on("EVEN")
					.to(evenStep())
				.end()
				.build();
	}
	
	@Bean
	public Step startStep() {
		return stepBuilderFactory.get("startStep")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> Start!");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step evenStep() {
		return stepBuilderFactory.get("evenStep")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> 짝수입니다.");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step oddStep() {
		return stepBuilderFactory.get("oddStep")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> 홀수입니다.");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public JobExecutionDecider oddEvenDecider() {
		return new OddEvenDecider();
	}
	
	public static class OddEvenDecider implements JobExecutionDecider {
		
		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
			Random random = new Random();
			
			int randomNumber = random.nextInt(50) + 1;
			log.info("랜덤 숫자 : {}" + randomNumber);
			
			if (randomNumber % 2 == 0) {
				return new FlowExecutionStatus("EVEN");
			} else {
				return new FlowExecutionStatus("ODD");
			}
		}
	}
}

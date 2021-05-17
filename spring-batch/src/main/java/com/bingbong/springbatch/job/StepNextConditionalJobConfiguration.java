package com.bingbong.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job stepNextConditionalJob() {
		return jobBuilderFactory.get("stepNextConditionalJob")
				.start(conditionalJobStep1()) // 시작
				.on("FAILED") // step1에서 FAILED했다면
				.to(conditionalJobStep3()) // step3로 이동하고 실행
				.on("*") // step3의 결과에 상관없이
				.end() // step 끝남
			.from(conditionalJobStep1()) // step1에서
				.on("*") // FAILED 이외라면
				.to(conditionalJobStep2()) // step2로 가서 실행해라
				.next(conditionalJobStep3()) // step2가 정상 종료되면 step3로 가라
				.on("*") // step3에 관계없이
				.end() // step 끝남
			.end() // job 끝남
			.build();
	}
	// 즉, step1 실패 -> step3
	// step1 성공 -> step2 -> step3
	
	@Bean
	public Step conditionalJobStep1() {
		return stepBuilderFactory.get("conditionalJobStep1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step1");
					
					// step1의 ExitStatus 상태를 FAILED로 지정한다.
					// 얘가 on을 통해서 조건 분기됨
					contribution.setExitStatus(ExitStatus.FAILED);
					
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step conditionalJobStep2() {
		return stepBuilderFactory.get("conditionalJobStep2")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step2");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step conditionalJobStep3() {
		return stepBuilderFactory.get("conditionalJobStep3")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step3");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
}

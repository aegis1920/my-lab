package com.bingbong.defguidespringbatch.chaptersbefore6.jobs;

import com.bingbong.defguidespringbatch.chaptersbefore6.service.CustomService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class MethodInvokingTaskletConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

//	@Bean
//	public Job methodInvokingJob() {
//		return this.jobBuilderFactory.get("methodInvokingJob")
//				.start(methodInvokingStep())
//				.build();
//	}
//
//	@Bean
//	public Step methodInvokingStep() {
//		return this.stepBuilderFactory.get("methodInvokingStep")
//				.tasklet(methodInvokingTasklet(null))
//				.build();
//	}
//
	
	/**
	 * MethodInvokingTaskletAdapter
	 *
	 * 다른 클래스에 있는 메서드를 tasklet으로 사용하고 싶을 때
	 * 굳이 Tasklet 인터페이스를 구현하지 않아도 된다.
	 * jobParameters를 활용해서 매개 값을 넣어줄 수도 있다
	 */
//	@StepScope
//	@Bean
//	public MethodInvokingTaskletAdapter methodInvokingTasklet(
//			@Value("#{jobParameters['message']}") String message) {
//
//		MethodInvokingTaskletAdapter methodInvokingTaskletAdapter =
//				new MethodInvokingTaskletAdapter();
//
//		methodInvokingTaskletAdapter.setTargetObject(service());
//		methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");
//		methodInvokingTaskletAdapter.setArguments(
//				new String[] {message});
//
//		return methodInvokingTaskletAdapter;
//	}

	@Bean
	public CustomService service() {
		return new CustomService();
	}

//	public static void main(String[] args) {
//		SpringApplication.run(MethodInvokingTaskletConfiguration.class, args);
//	}
}

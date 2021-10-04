package com.bingbong.defguidespringbatch.chaptersbefore6.jobs;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Callable;

@EnableBatchProcessing
@SpringBootApplication
public class CallableTaskletConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

//	@Bean
//	public Job callableJob() {
//		return this.jobBuilderFactory.get("callableJob")
//				.start(callableStep())
//				.build();
//	}
//
//	@Bean
//	public Step callableStep() {
//		return this.stepBuilderFactory.get("callableStep")
//				.tasklet(tasklet())
//				.build();
//	}

	@Bean
	public Callable<RepeatStatus> callableObject() {
		return () -> {
			System.out.println("This was executed in another thread");

			return RepeatStatus.FINISHED;
		};
	}
	
	/**
	 * CallableTaskletAdapter
	 *
	 * 해당 스레드가 아닌 다른 스레드에서 실행하고 싶을 때 사용
	 * 체크 예외를 바깥으로 던질 수 있다
	 * 다른 스레드에서 실행되나 병렬로 실행되는 것은 아니다
	 */
//	@Bean
//	public CallableTaskletAdapter tasklet() {
//		CallableTaskletAdapter callableTaskletAdapter =
//				new CallableTaskletAdapter();
//
//		callableTaskletAdapter.setCallable(callableObject());
//
//		return callableTaskletAdapter;
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(CallableTaskletConfiguration.class, args);
//	}
}

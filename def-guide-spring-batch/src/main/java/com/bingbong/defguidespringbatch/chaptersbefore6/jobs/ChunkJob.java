package com.bingbong.defguidespringbatch.chaptersbefore6.jobs;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class ChunkJob {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//
//	@Bean
//	public Job chunkBasedJob() {
//		return this.jobBuilderFactory.get("chunkBasedJob")
//				.start(chunkStep())
//				.build();
//	}
//
//	@Bean
//	public Step chunkStep() {
//		return this.stepBuilderFactory.get("chunkStep")
////				.<String, String>chunk(1000)
//				.<String, String>chunk(randomCompletionPolicy())
//				.reader(itemReader())
//				.writer(itemWriter())
//				.listener(new LoggingStepStartStopListener())
//				.build();
//	}
//
//	@Bean
//	public ListItemReader<String> itemReader() {
//		List<String> items = new ArrayList<>(100000);
//
//		for (int i = 0; i < 100000; i++) {
//			items.add(UUID.randomUUID().toString());
//		}
//
//		return new ListItemReader<>(items);
//	}
//
//	@Bean
//	public ItemWriter<String> itemWriter() {
//		return items -> {
//			for (String item : items) {
//				System.out.println(">> current item = " + item);
//			}
//		};
//	}
//
	/**
	 * 이처럼 정책을 함께 써줄 수 있다.
	 * CompletionPolicy를 구현할 수도 있다
	 * start 메서드가 구현체가 필요로 하는 내부 상태를 초기화한다
	 * 각 아이템이 처리될 때마다 update 메서드가 실행된다
	 * isComplete는 내부 상태를 이용해 청크 완료 여부를 판단할 수 있으며, RepeatStatus를 가지고도 청크 완료 여부 상태를 기반으로 로직을 수행할 수도 있다
	 */
//	@Bean
//	public CompletionPolicy completionPolicy() {
//		CompositeCompletionPolicy policy =
//				new CompositeCompletionPolicy();
//
//		policy.setPolicies(
//				new CompletionPolicy[] {
//						new TimeoutTerminationPolicy(3),
//						new SimpleCompletionPolicy(1000)});
//
//		return policy;
//	}
//
//	@Bean
//	public CompletionPolicy randomCompletionPolicy() {
//		return new RandomChunkSizePolicy();
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(ChunkJob.class, args);
//	}
}

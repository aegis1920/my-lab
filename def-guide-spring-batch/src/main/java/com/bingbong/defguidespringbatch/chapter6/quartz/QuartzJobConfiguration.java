package com.bingbong.defguidespringbatch.chapter6.quartz;

//@EnableBatchProcessing
//@SpringBootApplication
public class QuartzJobConfiguration {
//
//	@Configuration
//	public class BatchConfiguration {
//
//		@Autowired
//		private JobBuilderFactory jobBuilderFactory;
//
//		@Autowired
//		private StepBuilderFactory stepBuilderFactory;
//
//		@Bean
//		public Job job() {
//			return this.jobBuilderFactory.get("job")
//					.incrementer(new RunIdIncrementer())
//					.start(step1())
//					.build();
//		}
//
//		@Bean
//		public Step step1() {
//			return this.stepBuilderFactory.get("step1")
//					.tasklet((stepContribution, chunkContext) -> {
//						System.out.println("step1 ran!");
//						return RepeatStatus.FINISHED;
//					}).build();
//		}
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(QuartzJobConfiguration.class, args);
//	}
}

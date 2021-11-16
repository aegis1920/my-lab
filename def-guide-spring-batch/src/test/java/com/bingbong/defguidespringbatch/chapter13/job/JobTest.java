package com.bingbong.defguidespringbatch.chapter13.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

// BatchAutoConfig가 있어야 JobRepository DB가 생성된다.
@ContextConfiguration(classes = {JobTest.VerySimpleBatchConfiguration.class, BatchAutoConfiguration.class})
@SpringBatchTest
class JobTest {
	
	@Autowired
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@DisplayName("JobExecution과 StepExecution 간단 테스트")
	@Test
	void test() throws Exception {
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
		
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		
		StepExecution stepExecution = jobExecution.getStepExecutions()
				.iterator()
				.next();
		
		assertThat(stepExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(stepExecution.getReadCount()).isEqualTo(3);
		assertThat(stepExecution.getWriteCount()).isEqualTo(3);
	}
	
	@Configuration
	@EnableBatchProcessing
	public static class VerySimpleBatchConfiguration extends DefaultBatchConfigurer {
		
		private JobBuilderFactory jobBuilderFactory;
		private StepBuilderFactory stepBuilderFactory;
		
		@Autowired
		public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
			this.jobBuilderFactory = jobBuilderFactory;
		}
		
		@Autowired
		public void setStepBuilderFactory(StepBuilderFactory stepBuilderFactory) {
			this.stepBuilderFactory = stepBuilderFactory;
		}
		
		@Bean
		public ListItemReader<String> itemReader() {
			return new ListItemReader<>(Arrays.asList("foo", "bar", "baz"));
		}
		
		@Bean
		public ItemWriter<String> itemWriter() {
			return (list -> list.forEach(System.out::println));
		}
		
		@Bean
		public Step step() {
			return this.stepBuilderFactory.get("step")
					.<String, String>chunk(1)
					.reader(itemReader())
					.writer(itemWriter())
					.build();
		}
		
		@Bean
		public Job job() {
			return this.jobBuilderFactory.get("job")
					.start(step())
					.build();
		}
		
		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder()
					.build();
		}
	}
}

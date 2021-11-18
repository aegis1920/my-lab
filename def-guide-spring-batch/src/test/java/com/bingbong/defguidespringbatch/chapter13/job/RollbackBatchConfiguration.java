package com.bingbong.defguidespringbatch.chapter13.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@EnableBatchProcessing
@Slf4j
public class RollbackBatchConfiguration {
	
	@Setter
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Setter
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ListItemReader<String> itemReader() {
		List<String> numbers = IntStream.rangeClosed(1, 100)
				.boxed()
				.map(Object::toString)
				.collect(Collectors.toList());
		
		return new ListItemReader<>(numbers);
	}
	
	@Bean
	public ItemProcessor<String, String> itemProcessor() {
		return item -> {
			if (item.equals("9")) {
				throw new RuntimeException("에러가 터졌어요!!");
			}
			log.info("{} Process 진행중입니다...", item);
			return item + " Processed ";
		};
	}
	
	@Bean
	public ItemWriter<String> itemWriter() {
		return list -> list.forEach(System.out::print);
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
				.<String, String>chunk(5)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.listener(new CustomItemReadListener())
				.listener(new CustomItemProcessListener())
				.listener(new CustomItemWriterListener())
				.listener(new CustomChunkListener())
				.build();
	}
	
	@Bean
	public Job job1() {
		return this.jobBuilderFactory.get("job1")
				.start(step1())
				.build();
	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.build();
	}
}

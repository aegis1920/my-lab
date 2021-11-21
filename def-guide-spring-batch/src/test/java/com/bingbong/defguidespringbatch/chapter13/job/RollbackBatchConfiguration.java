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
	
	/**
	 * 3 -> null
	 * 9 -> Skip 가능한 Exception throw
	 * 13 -> Exception throw
	 */
	@Bean
	public ItemProcessor<String, String> itemProcessor() {
		return item -> {
			if ("3".equals(item)) {
				return null; // filterCount에 나온다.
			}
			if ("9".equals(item)) {
				throw new IllegalArgumentException("파라미터 에러가 터졌어요!!");
			}
			if ("13".equals(item)) {
				throw new RuntimeException("런타임 에러가 터졌어요!!");
			}
			log.info("{} Process 진행중입니다...", item);
			return item + " Processed ";
		};
	}
	
	@Bean
	public ItemWriter<String> itemWriter() {
		return list -> {
			list.forEach(System.out::print);
			System.out.println();
		};
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
				.<String, String>chunk(5)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.faultTolerant()
				.skip(IllegalArgumentException.class)
				.skipLimit(1)
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

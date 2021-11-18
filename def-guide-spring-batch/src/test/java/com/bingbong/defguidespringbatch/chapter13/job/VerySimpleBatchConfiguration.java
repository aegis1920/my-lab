package com.bingbong.defguidespringbatch.chapter13.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class VerySimpleBatchConfiguration {
	
	// Jackson도 테스트케이스 생성자 주입이 안된다. 오호... Runner가 테스트케이스를 생성하기 때문에 기본 생성자 + setter 방식으로 사용해야된다.
	// 테스트코드에 생성자 주입은 X
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
			log.info("{} Process 진행중입니다...", item);
			return item + " Processed ";
		};
	}
	
	@Bean
	@StepScope
	public ItemWriter<String> itemWriter(@Value("#{jobParameters['dryRun']}") Boolean dryRun) {
		if (dryRun == null) {
			return list -> {
				list.forEach(System.out::print);
				System.out.println();
			};
		}
		return dryRun ? list -> {}
				 : (list -> {
			list.forEach(System.out::print);
			System.out.println();
		});
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
				.<String, String>chunk(10)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter(null))
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

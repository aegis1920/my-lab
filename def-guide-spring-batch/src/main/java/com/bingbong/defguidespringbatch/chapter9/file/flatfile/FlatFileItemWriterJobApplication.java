package com.bingbong.defguidespringbatch.chapter9.file.flatfile;

import com.bingbong.defguidespringbatch.chapter9.file.flatfile.domain.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FlatFileItemWriterJobApplication extends DefaultBatchConfigurer {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	public FlatFileItemWriterJobApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public Job flatFileJob() {
		return this.jobBuilderFactory
				.get("flatFileJob")
				.start(flatFileStep())
				.build();
	}
	
	@Bean
	public Step flatFileStep() {
		return this.stepBuilderFactory.get("flatFileStep")
				.<Customer, Customer>chunk(2)
				.reader(customerFlatFileItemReader(null))
				.writer(customerItemWriter(null))
				.build();
	}
	
	// DefaultLineMapper를 생성하고 DefaultLineMapper는 DelimitedLineTokenizer를 사용해 라인을 파싱하고 BeanWrapperFieldSetMapper를 사용해 도메인 객체에 값을 채워넣는다.
	@Bean
	@StepScope
	public FlatFileItemReader<Customer> customerFlatFileItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {
		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerFlatFileItemReader")
				.resource(inputFile)
				.delimited()
				.names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip")
				.targetType(Customer.class)
				.build();
	}
	
	// 이 친구는 FormatterLineAggregator를 사용한다.
	@Bean
	@StepScope
	public FlatFileItemWriter<Customer> customerItemWriter(@Value("#{jobParameters['outputFile']}") Resource outputFile) {
		return new FlatFileItemWriterBuilder<Customer>()
				.name("customerItemWriter")
				.resource(outputFile)
				// format 형태로 출력하고 싶다면 이렇게
//				.formatted()
//				.format("%s %s lives at %s %s in %s, %s")
				// 구분자 형태로 출력하고 싶다면 이렇게
				.delimited()
				.delimiter(";")
				.names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip")
				// 스텝이 완료됐음에도 불구하고 아무 아이템도 기록되지 않는 경우, 출력 파일 삭제함
				// 예를 들어, chapter9_customer.csv 파일을 빈 상태로 뒀을 때 파일이 삭제됨
				// 다만 이때도 출력 파일이 생성되고 open, close 되긴 함
//				.shouldDeleteIfEmpty(true)
				// 파일이 이미 존재한다면 삭제하고 다시 생성한다.(기본값 true) false로 두면 파일이 이미 존재할 때 해당 job은 error가 뜬다.
				// 그래서 결과를 보호하기 위한 차원이라면 이 옵션을 false로 두는 게 좋다.
				.shouldDeleteIfExists(true)
				// 기존 값에 결과를 더할지 말지를 선택하게 한다.
//				.append(true)
				.build();
	}
	
	public static void main(String[] args) {
		List<String> realArgs = new ArrayList<>(Arrays.asList(args));
		
		realArgs.add("--job.name=flatFileJob");
		realArgs.add("customerFile=input/chapter9_customer.csv"); // project의 resource에 생성된다.
		realArgs.add("outputFile=file:output/chapter9/formattedCustomers.txt"); // project의 root에 생성된다.
		
		SpringApplication.run(FlatFileItemWriterJobApplication.class, realArgs.toArray(new String[realArgs.size()]));
	}
}

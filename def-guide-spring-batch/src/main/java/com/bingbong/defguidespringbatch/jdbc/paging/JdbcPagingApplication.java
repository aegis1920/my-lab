package com.bingbong.defguidespringbatch.jdbc.paging;

import com.bingbong.defguidespringbatch.jdbc.paging.domain.Customer;
import com.bingbong.defguidespringbatch.jdbc.paging.mapper.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableBatchProcessing
@SpringBootApplication
public class JdbcPagingApplication {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job customerJdbcPagingJob() {
		return this.jobBuilderFactory.get("customerJdbcPagingJob")
				.start(customerJdbcPagingStep())
				.build();
	}
	
	@Bean
	public Step customerJdbcPagingStep() {
		return this.stepBuilderFactory.get("customerJdbcPagingStep")
				.<Customer, Customer>chunk(2)
				.reader(customerJdbcPagingItemReader(null, null, null))
				.writer(customerJdbcPagingItemWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public JdbcPagingItemReader<Customer> customerJdbcPagingItemReader(DataSource dataSource, PagingQueryProvider queryProvider, @Value("#{jobParameters['city']}") String city) {
		Map<String, Object> parameterValues = new HashMap<>(1);
		parameterValues.put("city", city);
		
		return new JdbcPagingItemReaderBuilder<Customer>()
				.name("customerJdbcPagingItemReader")
				.dataSource(dataSource)
				.queryProvider(queryProvider)
				.parameterValues(parameterValues)
				.pageSize(10)
				.rowMapper(new CustomerRowMapper())
				.build();
	}
	
	@Bean
	public SqlPagingQueryProviderFactoryBean pagingQueryProvider(DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
		
		factoryBean.setSelectClause("select *");
		factoryBean.setFromClause("from customer");
		factoryBean.setWhereClause("where city = :city");
		factoryBean.setSortKey("lastName");
		factoryBean.setDataSource(dataSource);
		
		return factoryBean;
	}
	
	@Bean
	public ItemWriter<Customer> customerJdbcPagingItemWriter() {
		return items -> items.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JdbcPagingApplication.class, "--job.name=customerJdbcPagingJob", "city=Juneau");
	}
}

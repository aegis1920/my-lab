package com.bingbong.defguidespringbatch.chapter9.adapter;

import com.bingbong.defguidespringbatch.chapter9.adapter.domain.Customer;
import com.bingbong.defguidespringbatch.chapter9.adapter.service.CustomerAddressService;
import com.bingbong.defguidespringbatch.chapter9.adapter.service.CustomerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.adapter.PropertyExtractingDelegatingItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@EnableBatchProcessing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JpaAdapterApplication extends DefaultBatchConfigurer {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public ItemWriterAdapter<Customer> customerJpaAdapterItemWriter(CustomerService customerService) {
		ItemWriterAdapter<Customer> adapter = new ItemWriterAdapter<>();
		
		adapter.setTargetObject(customerService);
		adapter.setTargetMethod("getCustomer");
		
		return adapter;
	}
	
	@Bean
	public Job customerJpaAdapterJob() {
		return this.jobBuilderFactory.get("customerJpaAdapterJob")
				.start(customerJpaAdapterStep())
				.build();
	}
	
	@Bean
	public Step customerJpaAdapterStep() {
		return this.stepBuilderFactory.get("customerJpaAdapterStep")
				.<Customer, Customer>chunk(2)
				.reader(customerJpaAdapterItemReader(customerService))
				.writer(customerJpaAdapterItemWriter(null))
				.build();
	}
	
	@Bean
	public PropertyExtractingDelegatingItemWriter<Customer> propertyExtractingDelegatingItemWriter(CustomerAddressService customerAddressService) {
		PropertyExtractingDelegatingItemWriter<Customer> itemWriter = new PropertyExtractingDelegatingItemWriter<>();
		itemWriter.setTargetObject(customerAddressService);
		itemWriter.setTargetMethod("logCustomerAddress");
		itemWriter.setFieldsUsedAsTargetMethodArguments(new String[] {"address", "city", "state", "zip"});
		
		return itemWriter;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JpaAdapterApplication.class, "--job.name=customerJpaAdapterJob");
	}
}

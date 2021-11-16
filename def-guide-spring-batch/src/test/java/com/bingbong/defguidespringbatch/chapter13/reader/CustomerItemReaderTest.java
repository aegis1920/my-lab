package com.bingbong.defguidespringbatch.chapter13.reader;

import com.bingbong.defguidespringbatch.chapter13.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@ContextConfiguration(classes = CustomerBatchConfiguration.class)
class CustomerItemReaderTest {
	
	@Autowired
	private CustomerItemReader customerItemReader;
	
	@DisplayName("customerItemReader에서 읽었을 때, Customer 타입으로 읽는다.")
	@Test
	void testTypeConversion() {
		Customer customer = this.customerItemReader.read();
		
		assertThat(customer.getId()).isNotNull();
		assertThat(customer.getFirstName()).isNotNull();
		assertThat(customer.getMiddleInitial()).isNotNull();
		assertThat(customer.getLastName()).isNotNull();
		assertThat(customer.getAddress()).isNotNull();
		assertThat(customer.getCity()).isNotNull();
		assertThat(customer.getState()).isNotNull();
		assertThat(customer.getZipCode()).isNotNull();
		
		assertThat(this.customerItemReader.read())
				.isInstanceOf(Customer.class);
	}
}

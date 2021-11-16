package com.bingbong.defguidespringbatch.chapter13.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ContextConfiguration(classes = CustomerItemValidator.class)
@Sql(statements = "create table customer(id int);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = "DROP TABLE customer;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@JdbcTest
class CustomerItemValidatorIntegrationTests {
	
	@Autowired
	private DataSource dataSource; // @JdbcTest가 있으면 Default로 인메모리 DB를 사용한다.
	private NamedParameterJdbcTemplate template;
	private CustomerItemValidator customerItemValidator;
	
	@BeforeEach
	void setUp() {
		template = new NamedParameterJdbcTemplate(this.dataSource);
		this.customerItemValidator = new CustomerItemValidator(template);
	}
	
	@DisplayName("Customer ID가 5인 데이터가 있습니다.")
	@Test
	void testCustomers() {
		template.update("insert into customer(id) values (:id)", new HashMap<String, Long>() {{
			put("id", 5L);
		}});
		CustomerUpdate customerUpdate = new CustomerUpdate(5L);
		
		assertThatCode(() -> this.customerItemValidator.validate(customerUpdate))
				.doesNotThrowAnyException();
	}
	
	@DisplayName("Customer ID가 5인 데이터는 없습니다.")
	@Test
	void testNoCustomers() {
		CustomerUpdate customerUpdate = new CustomerUpdate(-5L);
		
		assertThatThrownBy(() -> this.customerItemValidator.validate(customerUpdate))
				.isInstanceOf(ValidationException.class)
				.hasMessageContaining("Customer id -5");
	}
}

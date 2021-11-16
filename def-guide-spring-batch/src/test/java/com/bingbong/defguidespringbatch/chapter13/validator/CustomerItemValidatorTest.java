package com.bingbong.defguidespringbatch.chapter13.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CustomerItemValidatorTest {
	
	@Mock
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private CustomerItemValidator validator;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // @ExtendWith(MockitoExtension.class) 이렇게 쓰기도 한다.
		this.validator = new CustomerItemValidator(this.jdbcTemplate);
	}
	
	@DisplayName("validator Mocking 테스트 성공")
	@Test
	void testValidCustomer() {
		CustomerUpdate customer = new CustomerUpdate(5L);
		
		ArgumentCaptor<Map<String, Long>> parameterMap = ArgumentCaptor.forClass(Map.class);
		
		when(this.jdbcTemplate.queryForObject(eq(CustomerItemValidator.FIND_CUSTOMER), parameterMap.capture(), eq(Long.class)))
				.thenReturn(2L);
		
		assertThatCode(() -> this.validator.validate(customer))
				.doesNotThrowAnyException();
		
		// 책에서 나온 테스트 코드는 의미가 없다.
//		assertThat(parameterMap.getValue().get("id")).isEqualTo(5L);
	}
	
	@DisplayName("validator Mocking 테스트 예외 던짐")
	@Test
	void testInvalidCustomer() {
		CustomerUpdate customerUpdate = new CustomerUpdate(5L);
		
		ArgumentCaptor<Map<String, Long>> parameterMap = ArgumentCaptor.forClass(Map.class);
		
		when(this.jdbcTemplate.queryForObject(eq(CustomerItemValidator.FIND_CUSTOMER), parameterMap.capture(), eq(Long.class)))
				.thenReturn(0L);
		
		assertThatThrownBy(() -> this.validator.validate(customerUpdate))
				.isInstanceOf(ValidationException.class);
	}
}
package com.bingbong.defguidespringbatch.chapter13.validator;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.Map;

public class CustomerItemValidator implements Validator<CustomerUpdate> {
	
	private final NamedParameterJdbcTemplate jdbcTemplate;
	
	public static final String FIND_CUSTOMER = "SELECT COUNT(*) FROM customer WHERE id = :id";
	
	public CustomerItemValidator(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void validate(CustomerUpdate customer) throws ValidationException {
		Map<String, Long> paratemerMap = Collections.singletonMap("id", customer.getId());
		
		Long count = jdbcTemplate.queryForObject(FIND_CUSTOMER, paratemerMap, Long.class);
		
		if (count == 0) {
			throw new ValidationException(String.format("Customer id %s 를 찾을 수 없습니다.", customer.getId()));
		}
	}
}

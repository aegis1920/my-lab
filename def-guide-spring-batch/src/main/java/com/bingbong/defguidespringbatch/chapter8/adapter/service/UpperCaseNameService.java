package com.bingbong.defguidespringbatch.chapter8.adapter.service;

import com.bingbong.defguidespringbatch.chapter8.adapter.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class UpperCaseNameService {

	/**
	 * targetMethod에 upperCase를 적는다.
	 */
	public Customer upperCase(Customer customer) {
		Customer newCustomer = new Customer(customer);

		newCustomer.setFirstName(newCustomer.getFirstName().toUpperCase());
		newCustomer.setMiddleInitial(newCustomer.getMiddleInitial().toUpperCase());
		newCustomer.setLastName(newCustomer.getLastName().toUpperCase());

		return newCustomer;
	}
}

package com.bingbong.defguidespringbatch.chapter8.composite.service;

import com.bingbong.defguidespringbatch.chapter8.composite.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class LowerCaseNameService {

	/**
	 * targetMethod에 lowerCase 적는다.
	 */
	public Customer lowerCase(Customer customer) {
		Customer newCustomer = new Customer(customer);

		newCustomer.setFirstName(newCustomer.getFirstName().toLowerCase());
		newCustomer.setMiddleInitial(newCustomer.getMiddleInitial().toLowerCase());
		newCustomer.setLastName(newCustomer.getLastName().toLowerCase());

		return newCustomer;
	}
}

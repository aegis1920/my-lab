package com.bingbong.defguidespringbatch.chapter9.adapter.service;

import com.bingbong.defguidespringbatch.chapter9.adapter.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	public void logCustomer(Customer customer) {
		System.out.println("I just saved " + customer);
	}
}

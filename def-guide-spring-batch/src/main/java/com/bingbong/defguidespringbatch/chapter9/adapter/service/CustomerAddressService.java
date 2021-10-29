package com.bingbong.defguidespringbatch.chapter9.adapter.service;

import com.bingbong.defguidespringbatch.chapter9.adapter.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerAddressService {
	
	public void logCustomer(Customer customer) {
		System.out.println("I just saved " + customer);
	}
	
	public void logCustomerAddress(String address, String city, String state, String zip) {
		System.out.println(String.format("I just saved the address : %s %s %s %s", address, city, state, zip));
	}
}

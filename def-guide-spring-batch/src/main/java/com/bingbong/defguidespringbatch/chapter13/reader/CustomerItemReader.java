package com.bingbong.defguidespringbatch.chapter13.reader;

import com.bingbong.defguidespringbatch.chapter13.domain.Customer;
import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ItemStreamSupport를 상속하면 고유 키를 생성하는 getExecutionContextKey라는 유틸리티 메서드를 제공할 뿐만 아니라 ItemStream 인터페이스도 구현한다.
 */
public class CustomerItemReader implements ItemReader<Customer> {
	private List<Customer> customers;
	private int curIndex;
	private String[] firstNames = {"Michael", "Warren", "Ann", "Terrence",
			"Erica", "Laura", "Steve", "Larry"};
	private String middleInitial = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String[] lastNames = {"Gates", "Darrow", "Donnelly", "Jobs",
			"Buffett", "Ellison", "Obama"};
	private String[] streets = {"4th Street", "Wall Street", "Fifth Avenue",
			"Mt. Lee Drive", "Jeopardy Lane",
			"Infinite Loop Drive", "Farnam Street",
			"Isabella Ave", "S. Greenwood Ave"};
	private String[] cities = {"Chicago", "New York", "Hollywood", "Aurora",
			"Omaha", "Atherton"};
	private String[] states = {"IL", "NY", "CA", "NE"};
	
	private Random generator = new Random();
	
	public CustomerItemReader() {
		curIndex = 0;
		customers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			customers.add(buildCustomer());
		}
	}
	
	private Customer buildCustomer() {
		Customer customer = new Customer();
		
		customer.setId((long) generator.nextInt(Integer.MAX_VALUE));
		customer.setFirstName(
				firstNames[generator.nextInt(firstNames.length - 1)]);
		customer.setMiddleInitial(
				String.valueOf(middleInitial.charAt(
						generator.nextInt(middleInitial.length() - 1))));
		customer.setLastName(
				lastNames[generator.nextInt(lastNames.length - 1)]);
		customer.setAddress(generator.nextInt(9999) + " " +
				streets[generator.nextInt(streets.length - 1)]);
		customer.setCity(cities[generator.nextInt(cities.length - 1)]);
		customer.setState(states[generator.nextInt(states.length - 1)]);
		customer.setZipCode(String.valueOf(generator.nextInt(99999)));
		
		return customer;
	}
	
	@Override
	public Customer read() {
		Customer cust = null;
		
		if (curIndex < customers.size()) {
			cust = customers.get(curIndex);
			curIndex++;
		}
		
		return cust;
	}
}

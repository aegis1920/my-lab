package com.bingbong.defguidespringbatch.chapter7.jdbc.paging.mapper;

import com.bingbong.defguidespringbatch.chapter7.jdbc.paging.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
	
	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		
		customer.setId(rs.getLong("id"));
		customer.setAddress(rs.getString("address"));
		customer.setCity(rs.getString("city"));
		customer.setFirstName(rs.getString("firstName"));
		customer.setLastName(rs.getString("lastName"));
		customer.setMiddleInitial(rs.getString("middleInitial"));
		customer.setState(rs.getString("state"));
		customer.setZipCode(rs.getString("zipCode"));
		
		return customer;
	}
}

package com.bingbong.defguidespringbatch.chapter7.jpa.repository.repository;

import com.bingbong.defguidespringbatch.chapter7.jpa.repository.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Page<Customer> findByCity(String city, Pageable pageRequest);
}

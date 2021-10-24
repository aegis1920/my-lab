package com.bingbong.defguidespringbatch.chapter9.database.repository.repository;

import com.bingbong.defguidespringbatch.chapter9.database.repository.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}

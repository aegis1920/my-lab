package com.bingbong.defguidespringbatch.chpater6.domain;

import java.util.List;

public interface TransactionDao {
	
	List<Transaction> getTransactionsByAccountNumber(String accountNumber);
}
package com.bingbong.defguidespringbatch.domain;

import java.util.List;

public interface TransactionDao {
	/**
	 * 계좌번호와 연관된 거래 목록 반환
	 */
	List<Transaction> getTransactionsByAccountNumber(String accountNumber);
}

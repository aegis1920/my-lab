package com.bingbong.defguidespringbatch.chpater6;

import com.bingbong.defguidespringbatch.chpater6.domain.AccountSummary;
import com.bingbong.defguidespringbatch.chpater6.domain.Transaction;
import com.bingbong.defguidespringbatch.chpater6.domain.TransactionDao;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class TransactionApplierProcessor implements
		ItemProcessor<AccountSummary, AccountSummary> {
	
	private TransactionDao transactionDao;
	
	public TransactionApplierProcessor(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	
	public AccountSummary process(AccountSummary summary) throws Exception {
		List<Transaction> transactions = transactionDao
				.getTransactionsByAccountNumber(summary.getAccountNumber());
		
		for (Transaction transaction : transactions) {
			summary.setCurrentBalance(summary.getCurrentBalance()
					+ transaction.getAmount());
		}
		return summary;
	}
}

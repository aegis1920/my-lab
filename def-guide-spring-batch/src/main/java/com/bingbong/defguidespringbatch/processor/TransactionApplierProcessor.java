/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bingbong.defguidespringbatch.processor;

import com.bingbong.defguidespringbatch.domain.AccountSummary;
import com.bingbong.defguidespringbatch.domain.Transaction;
import com.bingbong.defguidespringbatch.domain.TransactionDao;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;
/**
 * 전달받은 AccountSummary 레코드를 기반으로 TransactionDao를 사용해 모든 거래 정보를 조회한다.
 * 거래 정보에 따라 현재 잔액을 증가하거나 감소시킨다
 */
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

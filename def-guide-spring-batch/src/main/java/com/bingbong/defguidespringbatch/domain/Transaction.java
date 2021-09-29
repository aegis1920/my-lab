package com.bingbong.defguidespringbatch.domain;

import java.util.Date;

/**
 * Transaction 도메인 (데이터 모델)
 * 거래 정보를 뜻한다
 * AccountNumber와 Transaction는 1:N 관계
 *
 * acoountNumber : 계좌번호
 * amount : 거래량
 */
public class Transaction {

	private String accountNumber;

	private Date transactionDate;

	private double amount;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
}

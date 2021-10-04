package com.bingbong.defguidespringbatch.chapter6.transactionjob.domain;

/**
 * AccounSummary 도메인 (데이터 모델)
 * 간단한 계좌를 뜻한다
 *
 * accountNumber : 계좌번호
 * currentBalance : 카드사에서 해당 일에 승인한 총 금액
 */
public class AccountSummary {

	private int id;

	private String accountNumber;

	private Double currentBalance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
}

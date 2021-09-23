package com.bingbong.defguidespringbatch.domain

interface TransactionDao {

    /**
     * 계좌번호와 연관된 거래 목록 반환
     */
    fun getTransactionsByAccountNumber(accountNumber: String): List<Transaction>
}
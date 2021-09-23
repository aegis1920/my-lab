package com.bingbong.defguidespringbatch.processor

import com.bingbong.defguidespringbatch.domain.AccountSummary
import com.bingbong.defguidespringbatch.domain.TransactionDao
import org.springframework.batch.item.ItemProcessor

/**
 * 전달받은 AccountSummary 레코드를 기반으로 TransactionDao를 사용해 모든 거래 정보를 조회한다.
 * 거래 정보에 따라 현재 잔액을 증가하거나 감소시킨다
 */
class TransactionApplierProcessor(private val transactionDao: TransactionDao) :
    ItemProcessor<AccountSummary, AccountSummary> {
    override fun process(summary: AccountSummary): AccountSummary? {
        val transactions = transactionDao.getTransactionsByAccountNumber(summary.accountNumber)

        transactions.forEach { summary.currentBalance = summary.currentBalance + it.amount }
        return summary
    }
}
package com.bingbong.defguidespringbatch.domain.support

import com.bingbong.defguidespringbatch.domain.Transaction
import com.bingbong.defguidespringbatch.domain.TransactionDao
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import javax.sql.DataSource

class TransactionDaoSupport(dataSource: DataSource?) : JdbcTemplate(dataSource!!), TransactionDao {
    override fun getTransactionsByAccountNumber(accountNumber: String): List<Transaction> {
        return query(
            "select t.id, t.timestamp, t.amount " +
                    "from transaction t inner join account_summary a on " +
                    "a.id = t.account_summary_id " +
                    "where a.account_number = ?", arrayOf<Any>(accountNumber)
        ) { rs: ResultSet, _: Int ->
            Transaction(
                accountNumber = null,
                amount = rs.getDouble("amount"),
                timestamp = rs.getDate("timestamp")
            )
        }
    }
}
package com.bingbong.defguidespringbatch.chapter6.transactionjob.domain.support;

import com.bingbong.defguidespringbatch.chapter6.transactionjob.domain.Transaction;
import com.bingbong.defguidespringbatch.chapter6.transactionjob.domain.TransactionDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TransactionDaoSupport extends JdbcTemplate implements TransactionDao {

	public TransactionDaoSupport(DataSource dataSource) {
		super(dataSource);
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
		return query(
				"select t.id, t.timestamp, t.amount " +
						"from transaction t inner join account_summary a on " +
						"a.id = t.account_summary_id " +
						"where a.account_number = ?",
				new Object[] { accountNumber },
				(rs, rowNum) -> {
					Transaction trans = new Transaction();
					trans.setAmount(rs.getDouble("amount"));
					trans.setTransactionDate(rs.getDate("timestamp"));
					return trans;
				}
		);
	}
}

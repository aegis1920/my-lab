package com.bingbong.defguidespringbatch.chapter9.database.jdbc.setter;

import com.bingbong.defguidespringbatch.chapter9.database.jdbc.domain.Customer;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 각 Item의 값을 추출하고 PreparedStatement에 그 값을 세팅하는 인터페이스를 구현하는 구현체
 */
public class CustomerItemPreparedStatementSetter implements ItemPreparedStatementSetter<Customer> {
	@Override
	public void setValues(Customer item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getFirstName());
		ps.setString(2, item.getMiddleInitial());
		ps.setString(3, item.getLastName());
		ps.setString(4, item.getAddress());
		ps.setString(5, item.getCity());
		ps.setString(6, item.getState());
		ps.setString(7, item.getZip());
	}
}

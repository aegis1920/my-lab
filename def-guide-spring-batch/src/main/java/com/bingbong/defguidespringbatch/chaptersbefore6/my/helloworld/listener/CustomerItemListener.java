package com.bingbong.defguidespringbatch.chaptersbefore6.my.helloworld.listener;

import com.bingbong.defguidespringbatch.chaptersbefore6.my.helloworld.domain.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemReadListener;

/**
 * FlatFileParseException 라면 더 자세히 로그를 남길 수 있다.
 */
public class CustomerItemListener implements ItemReadListener<Customer> {
	private static final Log logger = LogFactory.getLog(CustomerItemListener.class);
	
	@Override
	public void beforeRead() {
		System.out.println("읽었습니다.");
	}
	
	@Override
	public void afterRead(Customer item) {
	
	}
	
	@Override
	public void onReadError(Exception e) {
	}
}

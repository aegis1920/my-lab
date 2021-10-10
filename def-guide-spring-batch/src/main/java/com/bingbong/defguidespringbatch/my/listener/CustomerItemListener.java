package com.bingbong.defguidespringbatch.my.listener;

import com.bingbong.defguidespringbatch.my.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemReadListener;

/**
 * FlatFileParseException 라면 더 자세히 로그를 남길 수 있다.
 */
@Slf4j
public class CustomerItemListener implements ItemReadListener<Customer> {
	private static final Log logger = LogFactory.getLog(CustomerItemListener.class);
	
	@Override
	public void beforeRead() {
		log.info("Item 단위 Read 시작합니다~");
	}
	
	@Override
	public void afterRead(Customer item) {
		log.info("Item 단위 Read 끝났습니다~");
	}
	
	@Override
	public void onReadError(Exception e) {
	}
}

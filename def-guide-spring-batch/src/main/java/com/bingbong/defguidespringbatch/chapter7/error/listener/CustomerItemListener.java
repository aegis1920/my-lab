package com.bingbong.defguidespringbatch.chapter7.error.listener;

import com.bingbong.defguidespringbatch.chapter7.error.domain.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * FlatFileParseException 라면 더 자세히 로그를 남길 수 있다.
 */
public class CustomerItemListener implements ItemReadListener<Customer> {
	private static final Log logger = LogFactory.getLog(CustomerItemListener.class);
	
	
	@Override
	public void beforeRead() {
	
	}
	
	@Override
	public void afterRead(Customer item) {
	
	}
	
	@Override
	public void onReadError(Exception e) {
		if (e instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) e;
			
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("An error occured while processing the " +
					ffpe.getLineNumber() +
					" line of the file.  Below was the faulty " +
					"input.\n");
			errorMessage.append(ffpe.getInput() + "\n");
			
			logger.error(errorMessage.toString(), ffpe);
		} else {
			logger.error("An error has occurred", e);
		}
	}
}

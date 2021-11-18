package com.bingbong.defguidespringbatch.chapter13.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CustomItemReadListener implements ItemReadListener<String> {
	@Override
	public void beforeRead() {
		log.info("-----------------------before Item Read----------------------");
		
	}
	
	@Override
	public void afterRead(String item) {
		log.info("item : {} 을 읽었습니다.", item);
		log.info("-----------------------after Item Read----------------------");
	}
	
	@Override
	public void onReadError(Exception ex) {
		log.info("-----------------------error Item Read----------------------");
	}
}

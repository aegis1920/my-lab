package com.bingbong.defguidespringbatch.chapter13.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

@Slf4j
public class CustomItemProcessListener implements ItemProcessListener<String, String> {
	
	@Override
	public void beforeProcess(String item) {
		log.info("-----------------------before Item Process----------------------");
	}
	
	@Override
	public void afterProcess(String item, String result) {
		log.info("-----------------------after Item Process----------------------");
		
	}
	
	@Override
	public void onProcessError(String item, Exception e) {
		log.info("-----------------------error Item Process----------------------");
		
	}
}

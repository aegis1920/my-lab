package com.bingbong.defguidespringbatch.chapter13.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

@Slf4j
public class CustomItemWriterListener implements ItemWriteListener<String> {
	
	@Override
	public void beforeWrite(List<? extends String> items) {
		log.info("-----------------------before Item Writer----------------------");
	}
	
	@Override
	public void afterWrite(List<? extends String> items) {
		log.info("-----------------------after Item Writer----------------------");
	}
	
	@Override
	public void onWriteError(Exception exception, List<? extends String> items) {
		log.info("-----------------------error Item Writer----------------------");
	}
}

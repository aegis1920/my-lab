package com.bingbong.defguidespringbatch.chapter13.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class CustomChunkListener implements ChunkListener {
	
	@Override
	public void beforeChunk(ChunkContext context) {
		log.info("-----------------------before Chunk----------------------");
	}
	
	@Override
	public void afterChunk(ChunkContext context) {
		log.info("-----------------------after Chunk----------------------");
	}
	
	@Override
	public void afterChunkError(ChunkContext context) {
		log.info("-----------------------error Chunk----------------------");
	}
}

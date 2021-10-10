package com.bingbong.defguidespringbatch.my.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class CustomerChunkListener implements ChunkListener {
	
	@Override
	public void beforeChunk(ChunkContext context) {
		log.info("Chunk 단위 시작합니다~");
	}
	
	@Override
	public void afterChunk(ChunkContext context) {
		log.info("Chunk 단위 끝났습니다~");
	}
	
	@Override
	public void afterChunkError(ChunkContext context) {
	
	}
}

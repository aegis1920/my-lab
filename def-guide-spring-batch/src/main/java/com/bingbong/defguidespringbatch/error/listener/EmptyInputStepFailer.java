package com.bingbong.defguidespringbatch.error.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;

/**
 * @AfterStep으로 Empty(빈) 값이 들어왔는지 확인이 가능하다.
 * 원래는 null이 들어오면 완료로 판단해버리기 때문
 */
public class EmptyInputStepFailer {
	
	@AfterStep
	public ExitStatus afterStep(StepExecution execution) {
		if (execution.getReadCount() > 0) {
			return execution.getExitStatus();
		} else {
			// 입력이 없으면 FAILED
			return ExitStatus.FAILED;
		}
	}
}

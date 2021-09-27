package com.bingbong.defguidespringbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * JobExecution은 Job이나 Step이 진행될 때 변경된다.
 * Job 상태는 JobExecution의 ExecutionContext에 저장된다. ExecutionContext는 JobRepository에 저장된다.
 * ExecutionContext는 잡의 상태를 Key, Value 형태로 저장하는 Batch Job의 세션이다
 * Job Execution, Step Execution 마다 여러 개의 Execution Context가 존재할 수 있다
 *
 */
public class ExecutionContextTasklet implements Tasklet {
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		/**
		 * job의 Execution Context 을 얻을 수 있는 방법은 아래 두 가지가 있다.
		 * 그러나 첫 번째 방법인 chunkContext.stepContext.jobExecutionContext 는 이 메서드가 반환한 Map을 변경하더라도 변경된 사항이 반영되지 않는다.
		 */
//        val jobContext = chunkContext.stepContext.jobExecutionContext
//        val jobContext = chunkContext.stepContext.stepExecution.jobExecution.executionContext
		// step의 Execution Context
		
		/**
		 * Step의 ExecutionContext에 name 데이터를 추가하고 있다.
		 * BATCH_JOB_EXECUTION_CONTEXT 테이블에 모두 저장된다.
		 */
		Object name = chunkContext.getStepContext().getJobParameters().get("name");
		ExecutionContext jobContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
		
		jobContext.put("name", name);
		System.out.println("Hello, 나는 Context에 있어요!!" + name);
		
		return RepeatStatus.FINISHED;
	}
}

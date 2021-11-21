package com.bingbong.defguidespringbatch.chapter13.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {RollbackBatchConfiguration.class, BatchAutoConfiguration.class})
@SpringBatchTest
class RollbackJobTest {
	
	@Autowired
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	
	@DisplayName("100개 중 3, 9, 13 item에 에러가 터진다면?")
	@Test
	void test4() throws Exception {
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(new JobParametersBuilder()
				.addString("hello", "world")
				.toJobParameters());
		
		assertThat(jobExecution.getJobInstance().getJobName()).isEqualTo("job1");
		assertThat(jobExecution.getFailureExceptions()).isEmpty();
		assertThat(jobExecution.getExecutionContext().isEmpty()).isTrue();
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.FAILED);
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
		assertThat(jobExecution.getExitStatus().getExitDescription()).contains("에러");
		assertThat(jobExecution.getStepExecutions()).hasSize(1);
		
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
		
		assertThat(stepExecution.getStepName()).isEqualTo("step1");
		assertThat(stepExecution.getReadCount()).isEqualTo(15); // 13 아이템에 RuntimeException을 throw할 때 FAILED 상태가 되고 종료된다. ChunkSize가 5이므로 15개까지 읽는다.
		assertThat(stepExecution.getWriteCount()).isEqualTo(8); // 3은 filter되고 9는 skip되고 13은 FAILED가 되어 Chunk 자체가 적용되지 않는다. 그래서 10 - 2 = 8이 된다.
		assertThat(stepExecution.getCommitCount()).isEqualTo(2); // 1 ~ 10까지, 2개의 Chunk는 Commit된다.
		assertThat(stepExecution.getRollbackCount()).isEqualTo(3); // 9에서 skip하더라도 예외를 던지기 때문에 rollback, 13에서 예외를 던지기 때문에 rollback, 13에서 Retry를 하다가 RetryException을 던지기 때문에 rollback이 된다.
		assertThat(stepExecution.getFilterCount()).isEqualTo(1); // 3에서 return null하기 때문
		assertThat(stepExecution.getProcessSkipCount()).isEqualTo(1); // 9에서 skip하기때문에 skipCount++
		assertThat(stepExecution.getStatus()).isEqualTo(BatchStatus.FAILED);
		assertThat(stepExecution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
		assertThat(stepExecution.getFailureExceptions()).hasSize(1);
		
		System.out.println("--------------------------같은 Job 다시 시작------------------------------");
		
		JobExecution jobExecution2 = this.jobLauncherTestUtils.launchJob(new JobParametersBuilder()
				.addString("hello", "world")
				.toJobParameters());
		
		assertThat(jobExecution2.getJobInstance().getJobName()).isEqualTo("job1");
		assertThat(jobExecution2.getFailureExceptions()).isEmpty();
		assertThat(jobExecution2.getExecutionContext().isEmpty()).isTrue();
		assertThat(jobExecution2.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(jobExecution2.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
		assertThat(jobExecution2.getStepExecutions()).hasSize(1);
		
		StepExecution stepExecution2 = jobExecution2.getStepExecutions().iterator().next();
		
		assertThat(stepExecution2.getStepName()).isEqualTo("step1");
		assertThat(stepExecution2.getReadCount()).isEqualTo(85); // 16부터 100까지 5개 item의 chunk로 읽어서 85번 읽음
		assertThat(stepExecution2.getWriteCount()).isEqualTo(85); // 읽었던 모든 item이 정상이므로 모두 write
		assertThat(stepExecution2.getCommitCount()).isEqualTo(18); // 총 17개의 chunk지만 commitCount가 18인 이유는 마지막을 의미하는 Chunk까지 읽어야하기 때문. 추가 커밋이 있음
		assertThat(stepExecution2.getRollbackCount()).isZero();
		assertThat(stepExecution2.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(stepExecution2.getExitStatus().getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());
		
	}
}

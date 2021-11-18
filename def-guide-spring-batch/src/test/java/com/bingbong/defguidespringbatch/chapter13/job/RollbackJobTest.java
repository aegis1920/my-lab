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
	
	
	@DisplayName("중간에 에러가 터진다면?")
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
		assertThat(stepExecution.getReadCount()).isEqualTo(10);
		assertThat(stepExecution.getWriteCount()).isEqualTo(5);
		assertThat(stepExecution.getCommitCount()).isEqualTo(1);
		assertThat(stepExecution.getRollbackCount()).isEqualTo(1);
		assertThat(stepExecution.getStatus()).isEqualTo(BatchStatus.FAILED);
		assertThat(stepExecution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
		assertThat(stepExecution.getFailureExceptions()).hasSize(1);
	}
}

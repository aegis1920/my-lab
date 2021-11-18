package com.bingbong.defguidespringbatch.chapter13.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

// BatchAutoConfig가 있어야 JobRepository DB가 생성된다.
// ApplicationContext를 빌드하는데 필요한 클래스 제공
@ContextConfiguration(classes = {VerySimpleBatchConfiguration.class, BatchAutoConfiguration.class})
// JobLauncherTestUtils 사용, Junit 5가 스프링 기능을 확장할 수 있게 하는 @ExtendWith(SpringExtenstion.class)도 있다.
@SpringBootTest
class JobTest {
	
	@Autowired
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@DisplayName("JobExecution과 StepExecution 간단 테스트")
	@Test
	void test1() throws Exception {
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
		
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		
		StepExecution stepExecution = jobExecution.getStepExecutions()
				.iterator()
				.next();
		
		assertThat(stepExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(stepExecution.getReadCount()).isEqualTo(100);
		assertThat(stepExecution.getWriteCount()).isEqualTo(100);
	}
	
	@DisplayName("JobParameters 테스트")
	@Test
	void test2() throws Exception {
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(
				new JobParametersBuilder()
						.addString("dryRun", "true")
						.toJobParameters()
		);
		
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		
		StepExecution stepExecution = jobExecution.getStepExecutions()
				.iterator()
				.next();
		
		assertThat(stepExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(stepExecution.getReadCount()).isEqualTo(100);
		assertThat(stepExecution.getWriteCount()).isEqualTo(100);
	}
	
	@DisplayName("jobExecution, stepExecution 파헤쳐보기")
	@Test
	void test3() throws Exception {
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
		
		assertThat(jobExecution.getJobParameters().getString("random")).isNotNull();
		assertThat(jobExecution.getJobInstance().getJobName()).isEqualTo("job1");
		assertThat(jobExecution.getFailureExceptions()).isEmpty();
		assertThat(jobExecution.getExecutionContext().isEmpty()).isTrue();
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
		assertThat(jobExecution.getStepExecutions()).hasSize(1);
		
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
		
		assertThat(stepExecution.getStepName()).isEqualTo("step1");
		assertThat(stepExecution.getReadCount()).isEqualTo(100);
		assertThat(stepExecution.getWriteCount()).isEqualTo(100);
		assertThat(stepExecution.getCommitCount()).isEqualTo(11);
		assertThat(stepExecution.getRollbackCount()).isZero();
		assertThat(stepExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		assertThat(stepExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
		assertThat(stepExecution.getFailureExceptions()).isEmpty();
	}
}

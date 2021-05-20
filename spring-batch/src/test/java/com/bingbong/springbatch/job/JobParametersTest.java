package com.bingbong.springbatch.job;

import com.bingbong.springbatch.config.H2ServerConfiguration;
import com.bingbong.springbatch.config.TestBatchConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

// 조금 더 오래 걸리긴 하지만 얘 하나만 사용해도 된다.
//@SpringBootTest(classes= {H2ServerConfiguration.class, SimpleJobConfiguration.class, TestBatchConfig.class})
@SpringBatchTest
@ContextConfiguration(classes= {H2ServerConfiguration.class, SimpleJobConfiguration.class, TestBatchConfig.class})
public class JobParametersTest {
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@DisplayName("JobParameter를 만들어서 넣어주기. Job은 알아서 찾는다")
	@Test
	public void testJob_WithJobParameters() throws Exception {
		//given
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addString("requestDate", "20210513");
		
		//when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(builder.toJobParameters());
		
		//then
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
	}
}

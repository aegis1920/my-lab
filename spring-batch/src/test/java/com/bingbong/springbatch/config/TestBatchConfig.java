package com.bingbong.springbatch.config;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBatchConfig {
	@Bean
	public JobLauncherTestUtils testJobLauncher() {
		return new JobLauncherTestUtils();
	}
}

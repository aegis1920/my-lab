package com.bingbong.defguidespringbatch.config

import org.quartz.JobExecutionContext
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean

class BatchScheduledJob(
    @Autowired private val quartzJob: Job,
    @Autowired private val jobExplorer: JobExplorer,
    @Autowired private val jobLauncher: JobLauncher,
    ): QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        val jobParameters = JobParametersBuilder(this.jobExplorer)
            .getNextJobParameters(this.quartzJob)
            .toJobParameters()

        try {
            this.jobLauncher.run(this.quartzJob, jobParameters)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
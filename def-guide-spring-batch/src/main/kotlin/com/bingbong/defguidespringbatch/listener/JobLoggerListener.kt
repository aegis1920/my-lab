package com.bingbong.defguidespringbatch.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class JobLoggerListener : JobExecutionListener{

    // Job이 시작할 때
    override fun beforeJob(jobExecution: JobExecution) {
        println("${jobExecution.jobInstance.jobName} is begging Execution")
    }

    // Job이 끝나고
    override fun afterJob(jobExecution: JobExecution) {
        println("${jobExecution.jobInstance.jobName} has Completed with the status : ${jobExecution.status}")
    }

}
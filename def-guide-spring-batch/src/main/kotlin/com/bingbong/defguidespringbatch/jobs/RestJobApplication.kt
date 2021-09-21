package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@EnableBatchProcessing
@SpringBootApplication
class RestJobApplication(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun restJob(): Job {
        return this.jobBuilderFactory
            .get("restJob")
            .incrementer(RunIdIncrementer())
            .start(restStep())
            .build()
    }

    @Bean
    fun restStep(): Step {
        return this.stepBuilderFactory.get("restStep")
            .tasklet { _, _ ->
                println("현재 이 Job 또한 실행되지 않음!!!")
                RepeatStatus.FINISHED
            }
            .build()
    }
}

@RestController
class JobLauncherController(
    @Autowired private val jobLauncher: JobLauncher,
    @Autowired private val context: ApplicationContext
) {
    @PostMapping("/run")
    fun runJob(@RequestBody request: JobLaunchRequest): ExitStatus {
        val job: Job = this.context
            .getBean(request.name, Job::class.java)

        return this.jobLauncher
            .run(job, request.getJobParameters())
            .exitStatus
    }
}

data class JobLaunchRequest(
    val name: String,
    val jobParameters: Properties,
) {
    fun getJobParameters(): JobParameters {
        val properties = Properties()
        properties.putAll(this.jobParameters)
        return JobParametersBuilder(properties).toJobParameters()
    }
}

fun main(args: Array<String>) {
    val application = SpringApplication(NoRunJobApplication::class.java)
    val properties = Properties()
    // 모든 Job이 실행되지 않음
    properties["spring.batch.job.enabled"] = false
    application.setDefaultProperties(properties)

    application.run(*args)
}


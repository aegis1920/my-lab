package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.*

@EnableBatchProcessing
@SpringBootApplication
class NoRunJobApplication(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun noRunJob(): Job {
        return this.jobBuilderFactory
            .get("noRunJob")
            .start(noRunStep())
            .build()
    }

    @Bean
    fun noRunStep(): Step {
        return this.stepBuilderFactory.get("noRunStep")
            .tasklet { _, _ ->
                println("현재 이 Job 또한 실행되지 않음!!!")
                RepeatStatus.FINISHED
            }
            .build()
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


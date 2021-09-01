package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
@EnableBatchProcessing
class HelloWorldJob(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun job(): Job {
        return this.jobBuilderFactory.get("basicJob")
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return this.stepBuilderFactory.get("step1")
            .tasklet { _, _ ->
                println("Hello World!")
                RepeatStatus.FINISHED
            }.build()
    }
}

fun main(args: Array<String>) {
    runApplication<HelloWorldJob>(*args)
}
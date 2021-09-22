package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableBatchProcessing
@SpringBootApplication
class QuartzJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun quartzJob(): Job {
        return this.jobBuilderFactory
            .get("quartzJob")
            .incrementer(RunIdIncrementer())
            .start(quartzStep())
            .build()
    }

    @Bean
    fun quartzStep(): Step {
        return this.stepBuilderFactory.get("quartzStep")
            .tasklet { _, _ ->
                println("Quartz로 Batch 실행시키기")
                RepeatStatus.FINISHED
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<QuartzJobConfiguration>(*args)
}


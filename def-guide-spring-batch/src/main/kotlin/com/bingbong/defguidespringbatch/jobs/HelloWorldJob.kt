package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
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
            .next(step2())
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

    @Bean
    fun step2(): Step {
        return this.stepBuilderFactory.get("step2")
            .tasklet(helloWorldTasklet())
            .build()
    }

    @Bean
    fun helloWorldTasklet(): Tasklet {
        // stepContribution은 아직 커밋되지 않은 현재 트랜잭션에 대한 정보
        // chunkContext는 실행 시점의 Job 상태 or Chunk와 관련된 정보. Job에 관련된 정보도 있
        return Tasklet { _, chunkContext: ChunkContext ->
            val name = chunkContext.stepContext
                .jobParameters["name"]
            println("Hello ${name}!")
            RepeatStatus.FINISHED
        }
    }
}

fun main(args: Array<String>) {
    runApplication<HelloWorldJob>(*args)
}
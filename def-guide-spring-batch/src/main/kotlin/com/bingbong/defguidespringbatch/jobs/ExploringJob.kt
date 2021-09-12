package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@EnableBatchProcessing
@SpringBootApplication
class ExploringJob(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
    @Autowired private val jobExplorer: JobExplorer,
) {

    @Bean
    fun explorerJob(): Job {
        return this.jobBuilderFactory
            .get("explorerJob")
            .start(explorerStep())
            .build()
    }

    @JobScope
    @Bean
    fun explorerStep(): Step {
        return this.stepBuilderFactory
            .get("explorerStep")
            .tasklet(explorerTasklet())
            .build()
    }

    @Bean
    fun explorerTasklet(): Tasklet {
        return ExploringTasklet(this.jobExplorer)
    }
}

class ExploringTasklet(
    private var explorer: JobExplorer,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val jobName = chunkContext.stepContext.jobName
        val instances = explorer.getJobInstances(jobName, 0, Int.MAX_VALUE)

        println("There are ${instances.size} job instances for the job $jobName")
        println("*****************결과는 아래있음*****************")

        instances.forEach {
            val jobExecutions = this.explorer.getJobExecutions(it)

            println("instance $it had ${jobExecutions.size}")

            jobExecutions.forEach { execution -> println("executionId : ${execution.id}, ExitStatus : ${execution.exitStatus}") }
        }
        return RepeatStatus.FINISHED
    }
}

fun main(args: Array<String>) {
    runApplication<ExploringJob>(*args)
}
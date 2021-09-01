package com.bingbong.defguidespringbatch.jobs

import com.bingbong.defguidespringbatch.validators.ParameterValidator
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
            .validator(validator())
            .next(step2())
            .next(step3())
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
    fun step3(): Step {
        return this.stepBuilderFactory.get("step3")
            .tasklet(helloWorldTaskletLateBiding(null)) // 늦게 binding 되기 때문에 null을 넣어준다.
            .build()
    }

    @Bean
    fun helloWorldTasklet(): Tasklet {
        // stepContribution은 아직 커밋되지 않은 현재 트랜잭션에 대한 정보
        // chunkContext는 실행 시점의 Job 상태 or Chunk와 관련된 정보. Job에 관련된 정보도 있다
        return Tasklet { _, chunkContext: ChunkContext ->
            val name = chunkContext.stepContext
                .jobParameters["name"]
            println("Hello ${name}!")
            RepeatStatus.FINISHED
        }
    }

    // 이 스코프를 사용해야 이 실행 범위에 왔을 때까지 Bean 생성을 지연시켜줄 수 있다. 그래서 잡 파라미터를 빈 생성 시점에 주입시켜줄 수 있다.
    @StepScope
    @Bean
    fun helloWorldTaskletLateBiding(@Value("#{jobParameters['name']}") name: String?): Tasklet {
        return Tasklet { _, _ ->
            println("Hello ${name}!!!!!!!!!!!!!!!!!!!!!")
            RepeatStatus.FINISHED
        }
    }

    @Bean
    fun validator(): CompositeJobParametersValidator {
        // CompositeJobParametersValidator를 통해 두 개의 Validator를 지정해줄 수 있다. 여기선 ParameterValidator 먼저 하고, 그 다음에 DefaultJobParametersValidator
        val validator = CompositeJobParametersValidator()
        // 기본적으로 지원해주는 Validator. requireKey와 OptionalKey 지원
        val defaultJobParametersValidator = DefaultJobParametersValidator(arrayOf("fileName"), arrayOf("name", "executionDate"))
        defaultJobParametersValidator.afterPropertiesSet()
        validator.setValidators(listOf(ParameterValidator(), defaultJobParametersValidator))

        return validator
    }
}

fun main(args: Array<String>) {
    runApplication<HelloWorldJob>(*args)
}
package com.bingbong.defguidespringbatch.jobs

import com.bingbong.defguidespringbatch.increment.DailyJobTimeStamper
import com.bingbong.defguidespringbatch.listener.JobLoggerListener
import com.bingbong.defguidespringbatch.validators.ParameterValidator
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.annotation.BeforeJob
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.listener.ExecutionContextPromotionListener
import org.springframework.batch.core.listener.JobListenerFactoryBean
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
            .incrementer(RunIdIncrementer()) // 얘는 기본 증가값. validator에 run.id를 추가해줘야된다. 근데 증분기를 위 아래 두 개나 해주니 얘는 run.id가 현재 값으로 고정된다. 아마 아래에 있는 것만 실행시키는듯?
            .incrementer(DailyJobTimeStamper()) // 얘 덕분에 잡 파라미터가 매번 바뀌어서 매번 잡 인스턴스를 만들기 때문에 편하게 실행시킬 수 있다.
            .next(step2())
            .next(step3())
            .listener(JobLoggerListener()) // 특정 시점에 로직을 실행시킬 수 있음
            .listener(JobListenerFactoryBean.getListener(loggerListener())) // 어노테이션용 Listener 근데 더 위 친구보다 더 빨리 실행된다. (얘가 먼저 출력됨)
            .next(step4())
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
    fun step4(): Step {
        return this.stepBuilderFactory.get("step4")
            .tasklet(ExecutionContextTasklet())
            .listener(promotionListener())
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
        // 새로운 JobParameter가 추가될 때마다 validator에도 추가해줘야한다.
        val defaultJobParametersValidator = DefaultJobParametersValidator(arrayOf("fileName"), arrayOf("name", "executionDate", "run.id", "currentDate"))
        defaultJobParametersValidator.afterPropertiesSet()
        validator.setValidators(listOf(ParameterValidator(), defaultJobParametersValidator))

        return validator
    }

    @BeforeJob
    fun loggerListener() {
        println("@BeforeJob 어노테이션으로 실행시켜보긔")
    }

    /**
     *
     * name 키가 Step의 ExecutionContext에 있을 때 name 키를 승격시켜줄 수 있다?
     * 스텝 간에 공유할 데이터가 있지만 첫 번째 스텝이 성공했을 때만 공유하게 하고 싶을 때 사용한다.
     * 성공적으로 종료되면 Step의 ExecutionContext에서 name 키를 찾아 Job의 ExecutionContext에 복사한다.
     * name 키를 찾지 못하면 기본적으로는 아무일도 일어나지 않지만 예외를 발생하게 할 수 있다.
     */
    @Bean
    fun promotionListener(): ExecutionContextPromotionListener {
        val listener = ExecutionContextPromotionListener()
        listener.setKeys(arrayOf("name"))

        return listener
    }
}

fun main(args: Array<String>) {
    runApplication<HelloWorldJob>(*args)
}
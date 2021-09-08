package com.bingbong.defguidespringbatch.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.Callable

@EnableBatchProcessing
@SpringBootApplication
class CallableTaskletConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun callableJob(): Job {
        return this.jobBuilderFactory
            .get("callableJob")
            .start(callableStep())
            .build()
    }

    @Bean
    fun callableStep(): Step {
        return this.stepBuilderFactory
            .get("callableStep")
            .tasklet(tasklet())
            .build()
    }

    /**
     * CallableTaskletAdapter
     *
     * 해당 스레드가 아닌 다른 스레드에서 실행하고 싶을 때 사용
     * 체크 예외를 바깥으로 던질 수 있다
     * 다른 스레드에서 실행되나 병렬로 실행되는 것은 아니다
     */
    @Bean
    fun tasklet(): CallableTaskletAdapter {
        val callableTaskletAdapter = CallableTaskletAdapter()
        callableTaskletAdapter.setCallable(callableObject())

        return callableTaskletAdapter
    }

    @Bean
    fun callableObject(): Callable<RepeatStatus> {
        return Callable {
            println("다른 스레드에서 실행된 스텝")
            RepeatStatus.FINISHED
        }
    }
}

fun main(args: Array<String>) {
    runApplication<CallableTaskletConfiguration>(*args)
}
package com.bingbong.defguidespringbatch.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableBatchProcessing
@SpringBootApplication
class MethodInvokingTaskletConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun methodInvokingJob(): Job {
        return this.jobBuilderFactory
            .get("methodInvokingJob")
            .start(methodInvokingStep())
            .build()
    }

    @Bean
    fun methodInvokingStep(): Step {
        return this.stepBuilderFactory
            .get("methodInvokingStep")
            .tasklet(methodInvokingTasklet(null))
            .build()
    }

    /**
     * MethodInvokingTaskletAdapter
     *
     * 다른 클래스에 있는 메서드를 tasklet으로 사용하고 싶을 때
     * 굳이 Tasklet 인터페이스를 구현하지 않아도 된다.
     * jobParameters를 활용해서 매개 값을 넣어줄 수도 있다
     */
    @StepScope
    @Bean
    fun methodInvokingTasklet(@Value("#{jobParameters['message']}") message: String?): MethodInvokingTaskletAdapter {
        val methodInvokingTaskletAdapter = MethodInvokingTaskletAdapter()
        methodInvokingTaskletAdapter.setTargetObject(service())
        methodInvokingTaskletAdapter.setTargetMethod("serviceMethod")
        methodInvokingTaskletAdapter.setArguments(arrayOf(message))

        return methodInvokingTaskletAdapter
    }

    @Bean
    fun service() = CustomService()
}

class CustomService {
    fun serviceMethod(message: String) {
        println("service method 입니당 message :  $message")
    }
}

fun main(args: Array<String>) {
    runApplication<MethodInvokingTaskletConfiguration>(*args)
}
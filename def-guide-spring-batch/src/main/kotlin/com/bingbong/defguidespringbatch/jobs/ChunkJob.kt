package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.CompletionPolicy
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.*

@EnableBatchProcessing
@SpringBootApplication
class ChunkJob(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun chunkBasedJob(): Job {
        return this.jobBuilderFactory
            .get("chunkBasedJob")
            .start(chunkStep())
            .build()
    }

    @Bean
    fun chunkStep(): Step {
        return this.stepBuilderFactory.get("chunkStep")
            .chunk<String, String>(completionPolicy())
            .reader(itemReader())
            .writer(itemWriter())
            .build()
    }

    @Bean
    fun itemReader(): ListItemReader<String> {
        val items = ArrayList<String?>(100000)

        for (i in 1..100000) {
            items.add(UUID.randomUUID().toString())
        }
        return ListItemReader(items)
    }

    @Bean
    fun itemWriter(): ItemWriter<String> {
        return ItemWriter { items: List<String> ->
            for (item in items) {
                println(">> current item : $item")
            }
        }
    }

    /**
     * 이처럼 정책을 함께 써줄 수 있다.
     * CompletionPolicy를 구현할 수도 있다
     * start 메서드가 구현체가 필요로 하는 내부 상태를 초기화한다
     * 각 아이템이 처리될 때마다 update 메서드가 실행된다
     * isComplete는 내부 상태를 이용해 청크 완료 여부를 판단할 수 있으며, RepeatStatus를 가지고도 청크 완료 여부 상태를 기반으로 로직을 수행할 수도 있다
     */
    @Bean
    fun completionPolicy(): CompletionPolicy {
        val policy = CompositeCompletionPolicy()
        policy.setPolicies(arrayOf(
            TimeoutTerminationPolicy(3),
            SimpleCompletionPolicy(1000)
        ))

        return policy
    }

}

fun main(args: Array<String>) {
    runApplication<ChunkJob>(*args)
}


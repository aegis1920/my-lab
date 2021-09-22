package com.bingbong.defguidespringbatch.jobs

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication

@EnableBatchProcessing
@SpringBootApplication
class RestJobApplication(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {
//
//    @Bean
//    fun restJob(): Job {
//        return this.jobBuilderFactory
//            .get("restJob")
//            .incrementer(RunIdIncrementer())
//            .start(restStep())
//            .build()
//    }
//
//    @Bean
//    fun restStep(): Step {
//        return this.stepBuilderFactory.get("restStep")
//            .tasklet { _, _ ->
//                println("REST API로 Batch 실행시키기")
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
//}
//
//@RestController
//class JobLauncherController(
//    @Autowired private val jobLauncher: JobLauncher,
//    @Autowired private val context: ApplicationContext,
//    @Autowired private val jobExplorer: JobExplorer,
//) {
//    @PostMapping("/run")
//    fun runJob(@RequestBody request: JobLaunchRequest): ExitStatus {
//        val job: Job = this.context
//            .getBean(request.name, Job::class.java)
//
//        val jobParameters = JobParametersBuilder(request.getJobParameters(), this.jobExplorer)
//            .getNextJobParameters(job) // 파라미터를 증가를 활성화시켜준다. 해당 Job이 JobParametersIncrementer를 가지고 있는지 판별한다. 이걸 가지고 있다면 마지막 JobExecution에 사용됐던 JobParameters에 적용한다.
//            .toJobParameters()
//
//        return this.jobLauncher
//            .run(job, jobParameters)
//            .exitStatus
//    }
//}
//
//data class JobLaunchRequest(
//    val name: String,
//    val jobParameters: Properties,
//) {
//    fun getJobParameters(): JobParameters {
//        val properties = Properties()
//        properties.putAll(this.jobParameters)
//        return JobParametersBuilder(properties).toJobParameters()
//    }
}

fun main(args: Array<String>) {
//    val application = SpringApplication(NoRunJobApplication::class.java)
//    val properties = Properties()
//    // 모든 Job이 실행되지 않음
//    properties["spring.batch.job.enabled"] = false
//    application.setDefaultProperties(properties)
//
//    application.run(*args)
}


package com.jongyun.chapter02

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class HelloWorld(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun step(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                println("Hello, World!")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun job(step: Step): Job {
        return jobBuilderFactory.get("job")
            .start(step)
            .build()
    }
}
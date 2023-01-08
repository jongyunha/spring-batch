package com.jongyun.chapter.ch04

import com.jongyun.chapter.ch04.validator.ParameterValidator
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SimpleJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val parameterValidator: ParameterValidator
) {

    @Bean
    fun simpleJob(simpleStep: Step): Job {
        return jobBuilderFactory.get("simpleJob")
            .start(simpleStep)
            .validator(parameterValidator)
            .build()
    }

    @Bean
    @JobScope
    fun simpleStep1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory.get("simpleStep1")
            .tasklet { contribution, chunkContext ->
                println(">>>>> requestDate = $requestDate")
                RepeatStatus.FINISHED
            }
            .build()
    }

}
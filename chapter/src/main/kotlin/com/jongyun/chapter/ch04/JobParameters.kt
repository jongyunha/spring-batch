package com.jongyun.chapter.ch04

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class JobParameters(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun step(tasklet: Tasklet): Step {
        return stepBuilderFactory.get("step1")
            .tasklet(tasklet)
            .build()
    }

    @Bean
    fun job(step: Step): Job {
        return jobBuilderFactory.get("job")
            .start(step)
            .build()
    }

    @Bean
    fun helloworldTasklet(): Tasklet {
        return Tasklet { contribution, chunkContext ->
            val name = chunkContext.stepContext.jobParameters["name"]
            println("name: $name")
            RepeatStatus.FINISHED
        }
    }

}
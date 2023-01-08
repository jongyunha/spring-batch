package com.jongyun.chapter.ch04

import com.jongyun.chapter.ch04.listner.JobLoggerListener
import com.jongyun.chapter.ch04.validator.ParameterValidator
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.listener.ExecutionContextPromotionListener
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SimpleJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val parameterValidator: ParameterValidator,
    val dailyJobTimestamper: DailyJobTimestamper,
    val jobLoggerListener: JobLoggerListener,
    val executionContextTasklet: ExecutionContextTasklet
) {

    @Bean
    fun simpleJob(simpleStep: Step): Job {
        return jobBuilderFactory.get("simpleJob")
            .start(simpleStep)
            .next(step2())
            .next(step3())
            .validator(parameterValidator)
            .incrementer(dailyJobTimestamper)
            .listener(stepExecutionListener())
            .build()
    }

    @JobScope
    @Bean
    fun simpleStep1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory.get("simpleStep1")
            .tasklet { contribution, chunkContext ->
                println(">>>>> requestDate = $requestDate")
                RepeatStatus.FINISHED
            }
            .build()
    }

    fun step2(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet(executionContextTasklet)
            .build()
    }

    fun step3(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet { contribution, chunkContext ->
                val name = chunkContext.stepContext.jobParameters["name"]
                println("Good Bye, $name")
                RepeatStatus.FINISHED
            }
            .build()
    }

    fun stepExecutionListener(): StepExecutionListener {
        // execution context 에서 name 의 키를 찾으면 복사한다.
        val listener = ExecutionContextPromotionListener()
        listener.setKeys(arrayOf("name"))
        return listener
    }
}

package com.jongyun.chapter.ch04

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ExecutionContextTasklet : Tasklet {
    companion object {
        const val HELLO_WORLD = "Hello, World!"
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val name = chunkContext.stepContext.jobParameters["name"]
        val executionContext = chunkContext.stepContext.stepExecution.executionContext
        executionContext.put("name", "$HELLO_WORLD, $name")
        println(">>>>> executionContext = $executionContext")
        return RepeatStatus.FINISHED
    }
}

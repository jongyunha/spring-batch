package com.jongyun.chapter.ch04.listner

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Component

@Component
class JobLoggerListener : JobExecutionListener {

    companion object {
        const val START_MESSAGE = "is beginning execution"
        const val END_MESSAGE = "has completed with the status"
    }

    override fun beforeJob(jobExecution: JobExecution) {
        println("Job ${jobExecution.jobInstance.jobName} $START_MESSAGE")
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("Job ${jobExecution.jobInstance.jobName} $END_MESSAGE ${jobExecution.status}")
    }
}

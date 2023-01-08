package com.jongyun.chapter.ch04

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class DailyJobTimestamper : JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters {
        return JobParametersBuilder(parameters!!)
            .addString("currentDate", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE))
            .toJobParameters()
    }
}

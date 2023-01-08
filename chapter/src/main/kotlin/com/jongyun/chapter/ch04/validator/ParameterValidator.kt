package com.jongyun.chapter.ch04.validator

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersValidator
import org.springframework.stereotype.Component

@Component
class ParameterValidator : JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        val requestDate = parameters?.getString("requestDate")
        if (requestDate == null || requestDate.length != 8) {
            throw IllegalArgumentException("requestDate must be specified with the format of yyyymmdd")
        }
    }
}
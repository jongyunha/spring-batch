package com.jongyun.chapter02

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class BesideBatchApplication

fun main(args: Array<String>) {
    runApplication<BesideBatchApplication>(*args)
}

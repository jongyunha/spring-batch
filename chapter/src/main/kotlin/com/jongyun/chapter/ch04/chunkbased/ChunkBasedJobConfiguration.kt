package com.jongyun.chapter.ch04.chunkbased

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChunkBasedJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val defaultItemWriter: DefaultItemWriter
) {

    @Bean
    fun chunkBasedJob() = jobBuilderFactory.get("chunkBasedJob")
        .start(chunkBasedStep())
        .build()

    fun chunkBasedStep() = stepBuilderFactory.get("chunkBasedStep")
        .chunk<String, String>(10)
        .reader(itemReader())
        .writer(defaultItemWriter)
        .build()

    @Bean
    fun itemReader(): ListItemReader<String> {
        val items = mutableListOf<String>()
        for (i in 1..1000) {
            items.add("$i")
        }
        return ListItemReader(items)
    }
}

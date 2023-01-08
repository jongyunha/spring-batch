package com.jongyun.chapter.ch04.chunkbased

import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
open class DefaultItemWriter : ItemWriter<String> {
    override fun write(items: List<String>) {
        items?.forEach {
            println(">> $it")
        }
    }
}

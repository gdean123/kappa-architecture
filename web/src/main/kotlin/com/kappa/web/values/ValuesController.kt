package com.kappa.web.values

import com.kappa.web.kafka.TopicWriter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
open class ValuesController(
    private val topicWriter: TopicWriter,
    private val valueRepository: ValueRepository
) {
    @RequestMapping(value = "/", method = arrayOf(RequestMethod.POST))
    fun create() {
        topicWriter.write("values-topic", 0, "Hello, World!")
    }

    @RequestMapping(value = "/", method = arrayOf(RequestMethod.GET))
    fun get(): List<String> {
        return valueRepository.findAll().map({ it.value })
    }
}
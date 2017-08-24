package com.kappa.web.values

import com.kappa.web.kafka.TopicWriter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ValuesController(
    private val topicWriter: TopicWriter,
    private val valueRepository: ValueRepository
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun create() {
        topicWriter.write("values-topic", 0, "Hello, World!")
    }

    @GetMapping("/")
    fun get(): List<String> {
        return valueRepository.findAll().map({ it.value })
    }
}
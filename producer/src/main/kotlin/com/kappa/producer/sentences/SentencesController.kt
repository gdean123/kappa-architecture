package com.kappa.producer.sentences

import com.kappa.producer.kafka.TopicWriter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SentencesController(private val topicWriter: TopicWriter) {
    @PostMapping("/sentences")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun create(@RequestBody createSentenceRequest: CreateSentenceRequest) {
        topicWriter.write("streams-plaintext-input", 0, createSentenceRequest.words)
    }
}
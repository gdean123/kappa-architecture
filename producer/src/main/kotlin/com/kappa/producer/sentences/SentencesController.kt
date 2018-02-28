package com.kappa.producer.sentences

import com.kappa.producer.kafka.TopicWriter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue
import java.util.UUID

@RestController
class SentencesController(private val topicWriter: TopicWriter) {
    @PostMapping("/sentences")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun create(@RequestBody createSentenceRequest: CreateSentenceRequest) {
        val key = SentenceCreatedKey(UUID.randomUUID().toString())
        val value = SentenceCreatedValue(createSentenceRequest.words)

        topicWriter.write("sentence_created", key, value)
    }
}
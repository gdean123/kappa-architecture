package com.kappa.producer.sentences

import com.kappa.producer.kafka.KafkaTopicWriter
import org.springframework.stereotype.Component
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue

@Component
class SentenceRepository(
    private val kafkaTopicWriter: KafkaTopicWriter,
    private val uuidGenerator: UuidGenerator
) {
    fun create(sentence: String) {
        val key = SentenceCreatedKey(uuidGenerator.generate())
        val value = SentenceCreatedValue(sentence)

        kafkaTopicWriter.write("sentence_created", key, value)
    }
}
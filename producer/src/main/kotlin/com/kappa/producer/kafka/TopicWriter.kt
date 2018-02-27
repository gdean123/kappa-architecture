package com.kappa.producer.kafka

import producer.SentenceCreatedKey
import producer.SentenceCreatedValue

interface TopicWriter {
    fun write(topic: String, key: SentenceCreatedKey, value: SentenceCreatedValue)
}
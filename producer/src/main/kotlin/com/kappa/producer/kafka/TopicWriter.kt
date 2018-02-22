package com.kappa.producer.kafka

interface TopicWriter {
    fun write(topic: String, key: Int?, value: String)
}
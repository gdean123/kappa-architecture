package com.kappa.web.kafka

interface TopicWriter {
    fun write(topic: String, key: Int?, value: String)
}
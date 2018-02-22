package com.kappa.producer.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

import java.util.HashMap

@Component
class KafkaTopicWriter(
    @Value("\${kafka.url}") private val kafkaUrl: String
) : TopicWriter {

    override fun write(topic: String, key: Int?, value: String) {
        val template = createTemplate(kafkaUrl)
        template.defaultTopic = topic
        template.sendDefault(key, value)
        template.flush()
    }

    private fun createTemplate(kafkaUrl: String): KafkaTemplate<Int, String> {
        val senderProperties = senderProperties(kafkaUrl)
        val producerFactory = DefaultKafkaProducerFactory<Int, String>(senderProperties)
        return KafkaTemplate(producerFactory)
    }

    private fun senderProperties(kafkaUrl: String): Map<String, Any> {
        val properties = HashMap<String, Any>()
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl)
        properties.put(ProducerConfig.RETRIES_CONFIG, 0)
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384)
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1)
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432)
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer::class.java)
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        return properties
    }
}

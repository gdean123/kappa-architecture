package com.kappa.producer.kafka

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaTopicWriter(
    @Value("\${kafka.url}") private val kafkaUrl: String,
    @Value("\${kafka.schemaRegistryUrl}") private val schemaRegistryUrl: String
) {
    fun <K, V> write(topic: String, key: K, value: V) {
        val template = createTemplate<K, V>(kafkaUrl)
        template.defaultTopic = topic
        template.sendDefault(key, value)
        template.flush()
    }

    private fun <K, V> createTemplate(kafkaUrl: String): KafkaTemplate<K, V> {
        val senderProperties = senderProperties(kafkaUrl)
        val producerFactory = DefaultKafkaProducerFactory<K, V>(senderProperties)
        return KafkaTemplate(producerFactory)
    }

    private fun senderProperties(kafkaUrl: String): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaUrl,
        ProducerConfig.RETRIES_CONFIG to 0,
        ProducerConfig.BATCH_SIZE_CONFIG to 16384,
        ProducerConfig.LINGER_MS_CONFIG to 1,
        ProducerConfig.BUFFER_MEMORY_CONFIG to 33554432,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl
    )
}

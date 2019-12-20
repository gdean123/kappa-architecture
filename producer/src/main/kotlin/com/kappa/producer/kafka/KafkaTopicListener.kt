package com.kappa.producer.kafka

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Component
import java.io.Closeable

@Component
class KafkaTopicListener(
    @Value("\${kafka.url}") private val kafkaUrl: String,
    @Value("\${kafka.schemaRegistryUrl}") private val schemaRegistryUrl: String
) : Closeable {
    private var container: KafkaMessageListenerContainer<Int, String>? = null

    fun <K, V> listen(topic: String, messageListener: MessageListener<K, V>) {
        val containerProperties = ContainerProperties(topic)
        containerProperties.messageListener = messageListener
        container = createContainer(containerProperties, kafkaUrl)
        container!!.start()
        Thread.sleep(1000)
    }

    private fun createContainer(containerProperties: ContainerProperties, kafkaUrl: String): KafkaMessageListenerContainer<Int, String> {
        val properties = consumerProperties(kafkaUrl)
        val consumerFactory = DefaultKafkaConsumerFactory<Int, String>(properties)
        return KafkaMessageListenerContainer(consumerFactory, containerProperties)
    }

    private fun consumerProperties(kafkaUrl: String) = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaUrl,
        ConsumerConfig.GROUP_ID_CONFIG to "some-group",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
        ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to "100",
        ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to "15000",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
        KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true
    )

    override fun close() {
        container!!.stop()
    }
}
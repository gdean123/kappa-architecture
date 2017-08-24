package com.kappa.web.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.listener.MessageListener
import org.springframework.kafka.listener.config.ContainerProperties
import org.springframework.stereotype.Component

import java.io.Closeable
import java.util.HashMap

@Component
open class KafkaTopicListener(
    @Value("\${kafka.url}") private val kafkaUrl: String
) : TopicListener, Closeable {
    private var container: KafkaMessageListenerContainer<Int, String>? = null

    override fun listen(topic: String, messageListener: MessageListener<Int, String>) {
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

    private fun consumerProperties(kafkaUrl: String): Map<String, Any> {
        val properties = HashMap<String, Any>()
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl)
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "some-group")
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true)
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100")
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000")
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer::class.java)
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        return properties
    }

    override fun close() {
        container!!.stop()
    }
}
package com.kappa.stream_processor.configuration

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.StreamsConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration

@Configuration
@EnableKafkaStreams
@EnableKafka
class KafkaStreamsConfiguration(
    @Value("\${kafka.url}") private val kafkaUrl: String,
    @Value("\${kafka.schemaRegistryUrl}") private val schemaRegistryUrl: String,
    @Value("\${application.id}") private val applicationId: String
) {
    @Bean(name = [(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)])
    fun streamsConfiguration(): StreamsConfig = StreamsConfig(mapOf<String, Any>(
        StreamsConfig.APPLICATION_ID_CONFIG to applicationId,
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaUrl,
        StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG to 0,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to SpecificAvroSerde::class.java,
        StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to SpecificAvroSerde::class.java,
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl
    ))
}

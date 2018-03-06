package com.kappa.consumer.word_counts

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import stream_processor.WordCountKey
import stream_processor.WordCountValue
import java.util.*

@Configuration
@EnableKafka
@EnableKafkaStreams
open class KafkaStreamsConfiguration(
    @Value("\${kafka.url}") private val kafkaUrl: String,
    @Value("\${kafka.schemaRegistryUrl}") private val schemaRegistryUrl: String,
    @Value("\${application.id}") private val applicationId: String
) {
    @Bean(name = arrayOf(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME))
    open fun kStreamsConfigs(): StreamsConfig {
        val properties = HashMap<String, Any>()
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId)
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl)
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0)
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)
        properties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl)
        return StreamsConfig(properties)
    }

    @Bean
    open fun kStream(kStreamBuilder: StreamsBuilder): KStream<WordCountKey, WordCountValue> {
        val source = kStreamBuilder.stream<WordCountKey, WordCountValue>("word_counts")

        source.foreach { key, value ->
            println("Processing ${key.getWord()}")
        }

        return source
    }
}

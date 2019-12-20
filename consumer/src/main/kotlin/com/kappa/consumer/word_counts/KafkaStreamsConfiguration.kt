package com.kappa.consumer.word_counts

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.state.QueryableStoreTypes
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.config.StreamsBuilderFactoryBean
import stream_processor.WordCountKey
import stream_processor.WordCountValue
import java.util.*

@Configuration
@EnableKafkaStreams
@EnableKafka
class KafkaStreamsConfiguration(
    @Value("\${confluent.schemaRegistryUrl}") private val schemaRegistryUrl: String,
    @Value("\${spring.application.name}") private val applicationName: String
) {
    @Bean
    fun kafkaStreams(kafkaProperties: KafkaProperties): KafkaStreams {
        val properties = streamsConfiguration(kafkaProperties.bootstrapServers)
        return KafkaStreams(kafkaStreamsTopology(), properties)
    }

    fun streamsConfiguration(bootstrapServers: List<String>): Properties {
        val properties = Properties()
        val map = mapOf(
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            StreamsConfig.APPLICATION_ID_CONFIG to applicationName,
            StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to SpecificAvroSerde::class.java,
            StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to SpecificAvroSerde::class.java,
            StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG to 0,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl
        )
        properties.putAll(map)

        return properties
    }

    @Bean
    fun kafkaStreamsTopology(): Topology {
        val streamsBuilder = StreamsBuilder()
        streamsBuilder.table<WordCountKey, WordCountValue>("word_counts", Materialized.`as`("word_counts"))
        return streamsBuilder.build()
    }

    @Bean
    fun wordCountsStore(streamsBuilderFactoryBean: StreamsBuilderFactoryBean): () -> ReadOnlyKeyValueStore<WordCountKey, WordCountValue> {
        return { streamsBuilderFactoryBean.kafkaStreams.store("word_counts", QueryableStoreTypes.keyValueStore()) }
    }
}

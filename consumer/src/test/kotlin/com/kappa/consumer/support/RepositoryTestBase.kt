package com.kappa.consumer.support

import com.kappa.consumer.word_counts.KafkaStreamsConfiguration
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.TopologyTestDriver
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.test.ConsumerRecordFactory
import org.junit.Before
import stream_processor.WordCountKey
import stream_processor.WordCountValue


abstract class RepositoryTestBase {
    protected lateinit var driver: TopologyTestDriver

    @Before
    fun repositoryTestBaseSetUp() {
        val configuration = KafkaStreamsConfiguration("http://localhost:8081", "some-application-id")
        driver = TopologyTestDriver(kafkaStreamsTopology(), configuration.streamsConfiguration(listOf("http://localhost:9092")))
    }

    protected fun emit(topic: String, key: WordCountKey, value: WordCountValue) {
        val factory = ConsumerRecordFactory<WordCountKey, WordCountValue>(topic, serializer(true), serializer(false))
        driver.pipeInput(factory.create(key, value))
    }

    private fun <T: SpecificRecord> serializer(isKey: Boolean): Serializer<T> {
        val serde = SpecificAvroSerde<T>()

        val properties = mapOf(
            AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081"
        )

        serde.configure(properties, isKey)
        return serde.serializer()
    }

    private fun kafkaStreamsTopology(): Topology {
        val streamsBuilder = StreamsBuilder()
        streamsBuilder.table<WordCountKey, WordCountValue>("word_counts", Materialized.`as`("word_counts"))
        return streamsBuilder.build()
    }
}

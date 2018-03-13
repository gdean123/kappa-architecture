package com.kappa.consumer.support

import com.kappa.consumer.word_counts.KafkaStreamsConfiguration
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.test.ProcessorTopologyTestDriver
import org.junit.Before
import stream_processor.WordCountKey
import stream_processor.WordCountValue

abstract class RepositoryTestBase {
    protected lateinit var driver: ProcessorTopologyTestDriver

    @Before
    fun repositoryTestBaseSetUp() {
        val configuration = KafkaStreamsConfiguration("http://some-host:1234", "http://localhost:8081", "some-application-id")
        driver = ProcessorTopologyTestDriver(configuration.streamsConfiguration(), topology(configuration))
    }

    protected fun emit(topic: String, key: WordCountKey, value: WordCountValue) {
        driver.process(topic, key, value, serializer(true), serializer(false))
    }

    private fun topology(kafkaStreamsConfiguration: KafkaStreamsConfiguration): Topology {
        val streamBuilder = StreamsBuilder()
        kafkaStreamsConfiguration.sink(streamBuilder)
        return streamBuilder.build()
    }

    private fun <T: SpecificRecord> serializer(isKey: Boolean): Serializer<T> {
        val serde = SpecificAvroSerde<T>()

        val properties = mapOf(
            AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081"
        )

        serde.configure(properties, isKey)
        return serde.serializer()
    }
}

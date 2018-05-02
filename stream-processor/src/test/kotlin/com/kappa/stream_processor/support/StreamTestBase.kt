package com.kappa.stream_processor.support

import com.kappa.stream_processor.configuration.KafkaStreamsConfiguration
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.test.ProcessorTopologyTestDriver
import org.junit.Before

abstract class StreamTestBase {
    private lateinit var driver: ProcessorTopologyTestDriver
    protected abstract fun stream(streamBuilder: StreamsBuilder)

    @Before
    fun streamTestBaseSetUp() {
        val configuration = KafkaStreamsConfiguration("http://some-host:1234", "http://localhost:8081", "some-application-id")
        driver = ProcessorTopologyTestDriver(configuration.streamsConfiguration(), topology())
    }

    protected fun <K: SpecificRecord, V: SpecificRecord> all(n: Int, topic: String) = Array(n, { next<K, V>(topic) })

    protected fun emit(topic: String, keyValuePair: Pair<SpecificRecord, SpecificRecord>) {
        driver.process(topic, keyValuePair.first, keyValuePair.second, serializer(true), serializer(false))
    }

    private fun topology(): Topology? {
        val streamBuilder = StreamsBuilder()
        stream(streamBuilder)
        return streamBuilder.build()
    }

    private fun <K: SpecificRecord, V: SpecificRecord> next(topic: String): Pair<K, V> {
        val record = driver.readOutput<K, V>(topic, deserializer(true), deserializer(false))
        return record.key() to record.value()
    }

    private fun <T: SpecificRecord> serializer(isKey: Boolean): Serializer<T> {
        return avroSerde<T>(isKey).serializer()
    }

    private fun <T: SpecificRecord> deserializer(isKey: Boolean): Deserializer<T> {
        return avroSerde<T>(isKey).deserializer()
    }

    private fun <T : SpecificRecord> avroSerde(isKey: Boolean): SpecificAvroSerde<T> {
        val serde = SpecificAvroSerde<T>()

        val properties = mapOf(
            AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081"
        )

        serde.configure(properties, isKey)
        return serde
    }
}

package stream_processor

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import java.util.*
import java.util.concurrent.CountDownLatch

object StreamProcessor {
    fun run(defineStream: (streamBuilder: StreamsBuilder, serializers: Serializers) -> Unit) {
        val serializers = Serializers("http://localhost:8081")
        val streamBuilder = StreamsBuilder()

        defineStream(streamBuilder, serializers)
        runSynchronously(streamBuilder.build())
    }

    private fun runSynchronously(stream: Topology) {
        try {
            start(stream).await()
        } catch (e: Throwable) {
            System.exit(1)
        }

        System.exit(0)
    }

    private fun start(stream: Topology): CountDownLatch {
        val streams = KafkaStreams(stream, properties())
        val latch = CountDownLatch(1)

        Runtime.getRuntime().addShutdownHook(Thread {
            streams.close()
            latch.countDown()
        })

        streams.start()
        return latch
    }

    private fun properties(): Properties {
        val properties = Properties()
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-processor")
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0)
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)
        properties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081")
        return properties
    }
}
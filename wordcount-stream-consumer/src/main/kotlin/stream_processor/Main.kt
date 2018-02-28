package stream_processor

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import java.util.*
import java.util.concurrent.CountDownLatch
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde

fun main(arguments: Array<String>) {
    val streamBuilder = StreamsBuilder()
    val source = streamBuilder.table<String, WordCount>("word_counts")

    source.toStream()
        .foreach { key, value -> println("Parsing $key: $value") }

    run(streamBuilder.build())
}

private fun run(stream: Topology) {
    val streams = KafkaStreams(stream, properties())
    val latch = CountDownLatch(1)

    Runtime.getRuntime().addShutdownHook(Thread {
        streams.close()
        latch.countDown()
    })

    try {
        streams.start()
        latch.await()
    } catch (e: Throwable) {
        System.exit(1)
    }

    System.exit(0)
}

private fun properties(): Properties {
    val properties = Properties()
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-stream-consumer")
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0)
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    properties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081")
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name)
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)

    return properties
}

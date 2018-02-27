package stream_processor

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Produced
import java.util.*
import java.util.concurrent.CountDownLatch

fun main(arguments: Array<String>) {
    val streamBuilder = StreamsBuilder()
    val source = streamBuilder.stream<String, String>("streams-plaintext-input")

    val counts = source
        .flatMapValues { line -> split(line) }
        .groupBy { _, value -> value }
        .count()
        .mapValues { count -> WordCount("some-word", count) }

    counts.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), wordCountSerializer()))
    run(streamBuilder.build())
}

private fun split(value: String) =
    Arrays.asList(*value.toLowerCase(Locale.getDefault()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

private fun wordCountSerializer(): Serde<WordCount>? {
    val valueSerde = Serdes.serdeFrom(SpecificAvroSerializer<WordCount>(), SpecificAvroDeserializer<WordCount>())
    valueSerde.configure(mapOf("schema.registry.url" to "http://localhost:8081"), false)
    return valueSerde
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
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount")
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0)
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name)
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name)
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    return properties
}

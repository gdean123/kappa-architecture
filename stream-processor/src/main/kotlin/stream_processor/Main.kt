package stream_processor

import java.util.concurrent.CountDownLatch
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import java.util.*

fun main(arguments: Array<String>) {
    val streamBuilder = StreamsBuilder()
    val source = streamBuilder.stream<String, String>("streams-plaintext-input")

    val counts = source
        .flatMapValues { line -> split(line) }
        .groupBy { _, value -> value }
        .count()

    counts.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()))
    run(streamBuilder.build())
}

private fun split(value: String) =
    Arrays.asList(*value.toLowerCase(Locale.getDefault()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

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
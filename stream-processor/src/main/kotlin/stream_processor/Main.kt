package stream_processor

import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.*
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.Serialized
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue
import java.util.*
import java.util.concurrent.CountDownLatch

fun main(arguments: Array<String>) {
    val streamBuilder = StreamsBuilder()
    val source = streamBuilder.stream<SentenceCreatedKey, SentenceCreatedValue>("sentence-created", Consumed.with(sentenceCreatedKeyDeserializer(), sentenceCreatedValueDeserializer()))

    val counts = source
        .flatMapValues { line -> split(line) }
        .map { _, word -> KeyValue(WordCountKey(word), word) }
        .groupByKey(Serialized.with(wordCountKeySerializer(), Serdes.String()))
        .count()
        .toStream()
        .map { wordCountKey, count -> KeyValue(wordCountKey, WordCountValue(wordCountKey.getWord(), count)) }

    counts.to("streams-wordcount-output", Produced.with(wordCountKeySerializer(), wordCountValueSerializer()))
    run(streamBuilder.build())
}

private fun split(value: SentenceCreatedValue) =
    Arrays.asList(*value.getSentence().toLowerCase(Locale.getDefault()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

private fun sentenceCreatedKeyDeserializer(): Serde<SentenceCreatedKey> {
    val keySerde = Serdes.serdeFrom(SpecificAvroSerializer<SentenceCreatedKey>(), SpecificAvroDeserializer<SentenceCreatedKey>())
    keySerde.configure(mapOf("schema.registry.url" to "http://localhost:8081"), true)
    return keySerde
}

private fun sentenceCreatedValueDeserializer(): Serde<SentenceCreatedValue> {
    val valueSerde = Serdes.serdeFrom(SpecificAvroSerializer<SentenceCreatedValue>(), SpecificAvroDeserializer<SentenceCreatedValue>())
    valueSerde.configure(mapOf("schema.registry.url" to "http://localhost:8081"), false)
    return valueSerde
}

private fun wordCountKeySerializer(): Serde<WordCountKey> {
    val keySerde = Serdes.serdeFrom(SpecificAvroSerializer<WordCountKey>(), SpecificAvroDeserializer<WordCountKey>())
    keySerde.configure(mapOf("schema.registry.url" to "http://localhost:8081"), true)
    return keySerde
}

private fun wordCountValueSerializer(): Serde<WordCountValue> {
    val valueSerde = Serdes.serdeFrom(SpecificAvroSerializer<WordCountValue>(), SpecificAvroDeserializer<WordCountValue>())
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
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java)
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    properties.put("schema.registry.url", "http://localhost:8081")
    return properties
}

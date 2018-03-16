package com.kappa.stream_processor.word_counts

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Serialized
import org.springframework.stereotype.Component
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue
import stream_processor.WordCountKey
import stream_processor.WordCountValue
import java.util.*
import javax.annotation.PostConstruct

@Component
class StreamProcessor(
    private val streamBuilder: StreamsBuilder
) {
    @PostConstruct
    fun stream() {
        val source = streamBuilder.stream<SentenceCreatedKey, SentenceCreatedValue>("sentence_created")

        val counts = source
            .peek { key, value -> println("Processing $key: $value") }
            .flatMapValues { sentenceCreatedValue -> split(sentenceCreatedValue.getSentence()) }
            .groupBy({ _, value -> value }, Serialized.with(Serdes.String(), Serdes.String()))
            .count()
            .toStream()
            .map { word, count -> KeyValue(WordCountKey(word), WordCountValue(word, count)) }
            .peek { key, value -> println("Emitting $key: $value") }

        counts.to("word_counts")
    }

    private fun split(sentence: String) =
        Arrays.asList(*sentence.toLowerCase(Locale.getDefault()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
}

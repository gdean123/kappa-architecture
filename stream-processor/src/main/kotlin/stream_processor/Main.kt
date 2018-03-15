package stream_processor

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Serialized
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue
import java.util.*

fun main(arguments: Array<String>) {
    StreamProcessor.run { streamBuilder, serializers ->
        val source = streamBuilder.stream<SentenceCreatedKey, SentenceCreatedValue>("sentence_created")

        val counts = source
            .peek { key, value -> println("Processing $key: $value") }
            .flatMapValues { sentenceCreatedValue -> split(sentenceCreatedValue.getSentence()) }
            .groupBy({ _, value -> value }, Serialized.with(serializers.string(), serializers.string()))
            .count()
            .toStream()
            .map { word, count -> KeyValue(WordCountKey(word), WordCountValue(word, count)) }
            .peek { key, value -> println("Emitting $key: $value") }

        counts.to("word_counts")
    }
}

private fun split(sentence: String) =
    Arrays.asList(*sentence.toLowerCase(Locale.getDefault()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

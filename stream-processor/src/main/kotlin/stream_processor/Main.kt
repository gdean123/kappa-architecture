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
            .flatMapValues { line -> split(line) }
            .map { _, word -> KeyValue(WordCountKey(word), word) }
            .groupByKey(Serialized.with(serializers.key(), serializers.string()))
            .count()
            .toStream()
            .map { wordCountKey, count -> KeyValue(wordCountKey, WordCountValue(wordCountKey.getWord(), count)) }

        counts.to("word_counts")
    }
}

private fun split(value: SentenceCreatedValue) =
    Arrays.asList(*value.getSentence().toLowerCase(Locale.getDefault()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

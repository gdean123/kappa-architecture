package com.kappa.consumer.word_counts

import org.apache.kafka.streams.state.QueryableStoreTypes
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore
import org.springframework.kafka.core.StreamsBuilderFactoryBean
import org.springframework.stereotype.Component
import stream_processor.WordCountKey
import stream_processor.WordCountValue

@Component
class WordCountRepository(
    private val streamsBuilderFactoryBean: StreamsBuilderFactoryBean
) {
    fun get(word: String): Long {
        val wordCountValue = wordCountsStore().get(WordCountKey(word))
        return wordCountValue.getCount()
    }

    private fun wordCountsStore(): ReadOnlyKeyValueStore<WordCountKey, WordCountValue> =
        streamsBuilderFactoryBean.kafkaStreams.store("word_counts", QueryableStoreTypes.keyValueStore())
}
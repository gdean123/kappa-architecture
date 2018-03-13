package com.kappa.consumer.word_counts

import org.apache.kafka.streams.state.ReadOnlyKeyValueStore
import org.springframework.stereotype.Component
import stream_processor.WordCountKey
import stream_processor.WordCountValue

@Component
class WordCountRepository(
    private val wordCountsStore: () -> ReadOnlyKeyValueStore<WordCountKey, WordCountValue>
) {
    fun get(word: String): Long {
        val wordCountValue = wordCountsStore().get(WordCountKey(word))
        return wordCountValue.getCount()
    }
}

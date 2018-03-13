package com.kappa.consumer.word_counts

import com.kappa.consumer.support.RepositoryTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import stream_processor.WordCountKey
import stream_processor.WordCountValue

class WordCountRepositoryTest: RepositoryTestBase() {
    private lateinit var wordCountRepository: WordCountRepository

    @Before
    fun setUp() {
        val wordCountsStore = { driver.getKeyValueStore<WordCountKey, WordCountValue>("word_counts") }
        wordCountRepository = WordCountRepository(wordCountsStore)
    }

    @Test
    fun `#get returns the latest count for the given word`() {
        emit("word_counts", WordCountKey("rainbows"), WordCountValue("rainbows", 123))
        val count = wordCountRepository.get("rainbows")
        assertThat(count).isEqualTo(123)
    }
}

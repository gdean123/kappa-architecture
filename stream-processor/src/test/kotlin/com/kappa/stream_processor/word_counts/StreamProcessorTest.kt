package com.kappa.stream_processor.word_counts

import com.kappa.stream_processor.support.StreamTestBase
import org.apache.kafka.streams.StreamsBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue
import stream_processor.WordCountKey
import stream_processor.WordCountValue

class StreamProcessorTest : StreamTestBase() {
    override fun stream(streamBuilder: StreamsBuilder) = StreamProcessor(streamBuilder).stream()

    @Test
    fun `#stream computes word counts`() {
        emit("sentence_created", SentenceCreatedKey("first-id"), SentenceCreatedValue("rainbows and sunshine"))
        emit("sentence_created", SentenceCreatedKey("second-id"), SentenceCreatedValue("alpine rainbows"))

        assertThat(all<WordCountKey, WordCountValue>(5, "word_counts")).isEqualTo(arrayOf(
            WordCountKey("rainbows") to WordCountValue("rainbows", 1),
            WordCountKey("and") to WordCountValue("and", 1),
            WordCountKey("sunshine") to WordCountValue("sunshine", 1),
            WordCountKey("alpine") to WordCountValue("alpine", 1),
            WordCountKey("rainbows") to WordCountValue("rainbows", 2)
        ))
    }
}

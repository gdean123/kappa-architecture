package com.kappa.producer.sentences

import com.kappa.producer.kafka.TopicWriter
import com.kappa.producer.support.ControllerTestBase
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class SentencesControllerTest : ControllerTestBase() {
    private lateinit var topicWriter: TopicWriter
    override fun controller() = SentencesController(topicWriter)

    @Before
    fun setUp() {
        topicWriter = mock()
    }

    @Test
    fun `#create emits the sentence to kafka`() {
        val response = post("/sentences", hashMapOf("words" to "rainbows and sunshine"))

        assertThat(response.statusCode).isEqualTo(202)
        verify(topicWriter).write("streams-plaintext-input", 0, "rainbows and sunshine")
    }
}
package com.kappa.producer.sentences

import com.kappa.producer.support.ControllerTestBase
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class SentencesControllerTest : ControllerTestBase() {
    private lateinit var sentenceRepository: SentenceRepository

    override fun controller() = SentencesController(sentenceRepository)

    @Before
    fun setUp() {
        sentenceRepository = mock()
    }

    @Test
    fun `#create emits the sentence to kafka`() {
        val response = post("/sentences", hashMapOf("words" to "rainbows and sunshine"))

        assertThat(response.statusCode).isEqualTo(202)
        verify(sentenceRepository).create("rainbows and sunshine")
    }
}
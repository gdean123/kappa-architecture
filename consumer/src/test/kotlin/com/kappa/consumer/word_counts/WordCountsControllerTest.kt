package com.kappa.consumer.word_counts

import com.kappa.consumer.support.ControllerTestBase
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat

class WordCountsControllerTest : ControllerTestBase() {
    private lateinit var wordCountRepository: WordCountRepository
    override fun controller() = WordCountsController(wordCountRepository)

    @Before
    fun setUp() {
        wordCountRepository = mock()
    }

    @Test
    fun `#get returns the count fetched from the word count repository`() {
        whenever(wordCountRepository.get("rainbows")).thenReturn(123)

        val response = get("/word_counts/rainbows")

        assertThat(response.statusCode).isEqualTo(200)
        assertThat(response.body.get("word").asText()).isEqualTo("rainbows")
        assertThat(response.body.get("count").asInt()).isEqualTo(123)
    }
}

package com.kappa.acceptance_tests

import com.kappa.acceptance_tests.support.Http
import com.kappa.acceptance_tests.values.CreateSentenceRequest
import com.kappa.acceptance_tests.values.WordCountResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AcceptanceTest {

    @Test
    fun `computes word counts`() {
        val response = Http.post("http://localhost:8080/sentences", CreateSentenceRequest("hiking in the hills"))
        assertThat(response.statusLine.statusCode).isEqualTo(202)

        val wordCountResponse = Http.get<WordCountResponse>("http://localhost:8181/word_counts/hills")
        assertThat(wordCountResponse.status).isEqualTo(200)
        assertThat(wordCountResponse.body.word).isEqualTo("hills")
        assertThat(wordCountResponse.body.count).isEqualTo(6)
    }
}

package com.kappa.web.values

import com.kappa.web.kafka.TopicWriter
import com.kappa.web.support.ControllerTestBase
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class ValuesControllerTest: ControllerTestBase() {
    @Inject private lateinit var valueRepository: ValueRepository
    private lateinit var topicWriter: TopicWriter

    @Before
    fun setUp() {
        topicWriter = mock()
    }

    override fun controller() = ValuesController(topicWriter, valueRepository)

    @Test
    fun `create writes hello world to the topic`() {
        val httpResponse = post("/", hashMapOf("name" to "Johnny"))
        assertThat(httpResponse.statusCode).isEqualTo(202)

        verify(topicWriter).write("values-topic", 0, "Hello, World!")
    }

    @Test
    fun `get returns values from the database`() {
        valueRepository.save(Value("Hello, World!"))
        val httpResponse = get("/")
        assertThat(httpResponse.statusCode).isEqualTo(200)
        assertThat(httpResponse.body?.get(0)?.asText()).isEqualTo("Hello, World!")
    }
}
package com.kappa.web.values

import com.kappa.web.kafka.TopicListener
import com.kappa.web.support.SpringTestBase
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.util.Lists
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.springframework.kafka.listener.MessageListener
import javax.inject.Inject

class ValueListenerTest : SpringTestBase() {
    @Inject private lateinit var valueRepository: ValueRepository
    private lateinit var mockTopicListener: TopicListener
    private lateinit var valueListener: ValueListener

    @Before
    fun setup() {
        mockTopicListener = mock()
        valueListener = ValueListener(mockTopicListener, valueRepository)
    }

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<MessageListener<Int, String>>

    @Test
    fun `listen persists the value to the database`() {
        valueListener.listen()

        verify(mockTopicListener).listen(eq("values-topic"), capture(argumentCaptor))
        argumentCaptor.value.onMessage(ConsumerRecord(
            "values-topic", 0, 0, 0, "Hello, World!"
        ))

        val values = Lists.newArrayList(valueRepository.findAll())
        assertThat(values).hasSize(1)
        assertThat(values[0].value).isEqualTo("Hello, World!")
    }
}

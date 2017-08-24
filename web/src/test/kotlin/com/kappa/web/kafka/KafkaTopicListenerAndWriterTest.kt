package com.kappa.web.kafka

import com.kappa.web.support.KafkaTestBase
import junit.framework.TestCase.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.kafka.listener.MessageListener
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class KafkaTopicListenerAndWriterTest: KafkaTestBase() {
    private lateinit var topicWriter: KafkaTopicWriter
    private lateinit var topicListener: KafkaTopicListener

    @Before
    fun setUp() {
        topicWriter = KafkaTopicWriter(kafkaUrl())
        topicListener = KafkaTopicListener(kafkaUrl())
    }

    @Test
    fun `listener returns written values`() {
        val latch = CountDownLatch(2)
        val receivedMessages = ArrayList<String>()
        topicListener.listen("values-topic", MessageListener({ message ->
            receivedMessages.add(message.value())
            latch.countDown()
        }))

        topicWriter.write("values-topic", 0, "foo")
        topicWriter.write("values-topic", 0, "bar")

        assertTrue(latch.await(60, TimeUnit.SECONDS))
        assertThat(receivedMessages.size).isEqualTo(2)
        assertThat(receivedMessages[0]).isEqualTo("foo")
        assertThat(receivedMessages[1]).isEqualTo("bar")
    }
}

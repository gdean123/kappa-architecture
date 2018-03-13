package com.kappa.producer.kafka

import com.kappa.producer.support.KafkaTestBase
import junit.framework.TestCase.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.kafka.listener.MessageListener
import producer.SentenceCreatedKey
import producer.SentenceCreatedValue
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class KafkaClientTest: KafkaTestBase() {
    private lateinit var topicWriter: KafkaTopicWriter
    private lateinit var topicListener: KafkaTopicListener

    override fun topic() = "values-topic"

    @Before
    fun setUp() {
        topicWriter = KafkaTopicWriter(kafkaUrl(), "http://localhost:8081")
        topicListener = KafkaTopicListener(kafkaUrl(), "http://localhost:8081")
    }

    @Test
    fun `listener returns written values`() {
        val latch = CountDownLatch(2)
        val receivedMessages = ArrayList<SentenceCreatedValue>()
        topicListener.listen("values-topic", MessageListener<SentenceCreatedKey, SentenceCreatedValue>({ message ->
            receivedMessages.add(message.value())
            latch.countDown()
        }))

        topicWriter.write("values-topic", SentenceCreatedKey("first-id"), SentenceCreatedValue("first-content"))
        topicWriter.write("values-topic", SentenceCreatedKey("second-id"), SentenceCreatedValue("second-content"))

        assertTrue(latch.await(5, TimeUnit.SECONDS))
        assertThat(receivedMessages.size).isEqualTo(2)
        assertThat(receivedMessages[0].getSentence()).isEqualTo("first-content")
        assertThat(receivedMessages[1].getSentence()).isEqualTo("second-content")
    }
}

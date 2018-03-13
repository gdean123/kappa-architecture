package com.kappa.producer.support

import org.junit.After
import org.junit.Before
import org.springframework.kafka.test.rule.KafkaEmbedded

abstract class KafkaTestBase {
    private lateinit var embeddedKafka: KafkaEmbedded
    protected abstract fun topic(): String

    @Before
    fun kafkaTestBaseSetUp() {
        embeddedKafka = KafkaEmbedded(1, true, topic())
        embeddedKafka.before()
    }

    protected fun kafkaUrl(): String = embeddedKafka.brokersAsString

    @After
    fun kafkaTestBaseTearDown() {
        embeddedKafka.after()
    }
}

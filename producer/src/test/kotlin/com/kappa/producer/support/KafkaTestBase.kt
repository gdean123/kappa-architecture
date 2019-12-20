package com.kappa.producer.support

import org.apache.kafka.clients.admin.NewTopic
import org.junit.Before
import org.junit.ClassRule
import org.springframework.kafka.test.rule.EmbeddedKafkaRule

abstract class KafkaTestBase {
    protected abstract fun topic(): String

    companion object {
        @ClassRule @JvmField
        var embeddedKafkaRule = EmbeddedKafkaRule(1, true, 5)
    }

    @Before
    fun kafkaTestBaseSetUp() {
        embeddedKafkaRule.embeddedKafka.addTopics(NewTopic(topic(), 15, 1.toShort()))
    }

    protected fun kafkaUrl(): String = embeddedKafkaRule.embeddedKafka.brokersAsString
}

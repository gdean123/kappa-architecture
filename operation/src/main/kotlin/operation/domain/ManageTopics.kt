package operation.domain

import operation.tools.KafkaConsoleConsumer
import operation.tools.KafkaConsoleProducer
import operation.tools.KafkaTopics

object ManageTopics {
    fun list() {
        KafkaTopics.list()
    }

    fun create() {
        KafkaTopics.create("streams-plaintext-input")
        KafkaTopics.create("streams-wordcount-output")
    }

    fun destroy() {
        KafkaTopics.delete("streams-plaintext-input")
        KafkaTopics.delete("streams-wordcount-output")
    }

    fun publish() {
        KafkaConsoleProducer.publish("streams-plaintext-input")
    }

    fun subscribe() {
        KafkaConsoleConsumer.subscribe("streams-wordcount-output")
    }
}

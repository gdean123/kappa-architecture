package operation.domain

import operation.tools.KafkaConsoleConsumer
import operation.tools.KafkaConsoleProducer
import operation.tools.KafkaTopics

object ManageTopics {
    fun list() {
        KafkaTopics.list()
    }

    fun create() {
        KafkaTopics.create("sentence-created")
        KafkaTopics.create("streams-wordcount-output")
    }

    fun destroy() {
        KafkaTopics.delete("sentence-created")
        KafkaTopics.delete("streams-wordcount-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog")
        KafkaTopics.delete("streams-wordcount-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition")
        KafkaTopics.delete("streams-wordcount-output")
    }

    fun publish() {
        KafkaConsoleProducer.publish("sentence-created")
    }

    fun subscribe() {
        KafkaConsoleConsumer.subscribe("streams-wordcount-output")
    }
}

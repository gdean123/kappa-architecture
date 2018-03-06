package operation.domain.actions

import operation.tools.KafkaConsoleConsumer
import operation.tools.KafkaConsoleProducer
import operation.tools.KafkaTopics

object ManageTopics {
    fun list() {
        KafkaTopics.list()
    }

    fun create() {
        KafkaTopics.create("sentence_created")
        KafkaTopics.create("word_counts")
    }

    fun destroy() {
        KafkaTopics.delete("sentence_created")
        KafkaTopics.delete("sentence_created-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog")
        KafkaTopics.delete("sentence_created-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition")
        KafkaTopics.delete("word_counts")
    }

    fun publish() {
        KafkaConsoleProducer.publish("sentence_created")
    }

    fun subscribe(topic: String) {
        KafkaConsoleConsumer.subscribe(topic)
    }
}

package operation.tools

import operation.support.Shell

object KafkaConsoleProducer {
    fun publish(topic: String) {
        Shell.execute(listOf(
            "kafka-console-producer",
            "--broker-list", "localhost:9092",
            "--topic", topic
        ))
    }
}

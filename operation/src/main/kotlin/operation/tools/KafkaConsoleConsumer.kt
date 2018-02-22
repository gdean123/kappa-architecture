package operation.tools

import operation.support.Shell

object KafkaConsoleConsumer {
    fun subscribe(topic: String) {
        Shell.execute(listOf(
            "kafka-console-consumer",
            "--bootstrap-server", "localhost:9092",
            "--topic", topic,
            "--from-beginning",
            "--formatter", "kafka.tools.DefaultMessageFormatter",
            "--property", "print.key=true",
            "--property", "print.value=true",
            "--property", "key.deserializer=org.apache.kafka.common.serialization.StringDeserializer",
            "--property", "value.deserializer=org.apache.kafka.common.serialization.LongDeserializer"
        ))
    }
}


package operation.tools

import operation.support.Shell

object KafkaConsoleConsumer {
    fun subscribe(topic: String) {
        Shell.execute(listOf(
            "kafka-avro-console-consumer",
            "--bootstrap-server", "localhost:9092",
            "--topic", topic,
            "--from-beginning",
            "--property", "print.key=true",
            "--property", "print.value=true"
        ))
    }
}

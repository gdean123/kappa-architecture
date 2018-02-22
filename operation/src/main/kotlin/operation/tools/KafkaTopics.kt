package operation.tools

import operation.support.Shell

object KafkaTopics {
    fun list() {
        Shell.execute(listOf(
            "kafka-topics", "--list",
            "--zookeeper", "localhost:2181"
        ))
    }

    fun create(topic: String) {
        Shell.execute(listOf(
            "kafka-topics", "--create",
            "--zookeeper", "localhost:2181",
            "--replication-factor", "1",
            "--partitions", "1",
            "--topic", topic
        ))
    }

    fun delete(topic: String) {
        Shell.execute(listOf(
            "kafka-topics", "--delete",
            "--zookeeper", "localhost:2181",
            "--topic", topic
        ))
    }
}
package operation.domain

import operation.support.Paths
import operation.support.Shell

object StartProducer {
    fun execute() {
        Shell.execute("./gradlew build bootRun -x test", Paths.web(), mapOf(
            "KAFKA_URL" to "http://localhost:9092"
        ))
    }
}
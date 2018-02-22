package operation.domain

import operation.support.Paths
import operation.support.Shell

object RunTests {
    fun execute() {
        Shell.execute("./gradlew clean cleanTest test", Paths.producer(), mapOf(
            "KAFKA_URL" to "http://localhost:9092"
        ))
    }
}

package operation.domain.actions

import operation.support.Paths
import operation.tools.Gradle

object RunTests {
    fun producer() {
        Gradle.test(Paths.producer(), mapOf(
            "KAFKA_URL" to "http://localhost:9092"
        ))
    }
}

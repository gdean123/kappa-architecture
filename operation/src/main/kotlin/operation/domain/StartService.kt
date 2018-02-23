package operation.domain

import operation.support.Paths
import operation.tools.Gradle

object StartService {
    fun producer() = Gradle.bootRun(Paths.producer(), mapOf("KAFKA_URL" to "http://localhost:9092"))
    fun streamProcessor() = Gradle.run(Paths.streamProcessor())
}
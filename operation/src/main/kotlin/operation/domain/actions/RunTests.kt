package operation.domain.actions

import operation.support.Environment
import operation.support.Paths
import operation.tools.Gradle

object RunTests {
    fun producer() = Gradle.test(Paths.producer(), Environment.read("producer", "test", "application"))
    fun consumer() = Gradle.test(Paths.consumer(), Environment.read("consumer", "test", "application"))
    fun streamProcessor() = Gradle.test(Paths.streamProcessor(), Environment.read("stream-processor", "test", "application"))
}

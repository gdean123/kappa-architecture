package operation.domain.actions

import operation.support.Environment
import operation.support.Paths
import operation.tools.Gradle

object StartService {
    fun producer() =
        Gradle.bootRun(Paths.producer(), Environment.read("producer", "development", "application"))

    fun streamProcessor() =
        Gradle.bootRun(Paths.streamProcessor(), Environment.read("stream-processor", "development", "application"))

    fun consumer() =
        Gradle.bootRun(Paths.consumer(), Environment.read("consumer", "development", "application"))
}
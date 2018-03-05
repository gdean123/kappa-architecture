package operation.domain

import operation.support.Environment
import operation.support.Paths
import operation.tools.Gradle

object StartService {
    fun producer() =
        Gradle.bootRun(Paths.producer(), Environment.read("producer", "development", "application"))

    fun streamProcessor() =
        Gradle.run(Paths.streamProcessor(), Environment.read("stream-processor", "development", "application"))
}
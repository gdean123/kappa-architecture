package operation.domain

import operation.support.Environment
import operation.support.Paths
import operation.tools.Gradle

object StartService {
    fun producer() = Gradle.bootRun(Paths.producer(), Environment.read("producer"))
    fun streamProcessor() = Gradle.run(Paths.streamProcessor(), Environment.read("stream-processor"))
}
package operation.domain

import operation.support.Paths
import operation.tools.Gradle

object BuildArtifact {
    fun all() {
        Gradle.jar(Paths.producer())
        Gradle.jar(Paths.streamProcessor())
    }
}

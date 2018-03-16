package operation.tools

import operation.support.Shell
import java.io.File

object Gradle {
    fun test(path: File, environmentVariables: Map<String, String>) =
        Shell.execute("./gradlew clean cleanTest test", path, environmentVariables)

    fun bootRun(path: File, environmentVariables: Map<String, String>) =
        Shell.execute("./gradlew build bootRun -x test", path, environmentVariables)

    fun jar(path: File) {
        Shell.execute("./gradlew clean jar", path)
    }
}
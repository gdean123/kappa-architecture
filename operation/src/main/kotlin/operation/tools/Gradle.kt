package operation.tools

import operation.support.Shell
import java.io.File

object Gradle {
    fun test(path: File, environmentVariables: Map<String, String>) =
        Shell.execute("./gradlew clean cleanTest test", path, environmentVariables)

    fun run(path: File) =
        Shell.execute("./gradlew generateAvroJava run -x test", path)

    fun bootRun(path: File, environmentVariables: Map<String, String>) =
        Shell.execute("./gradlew build bootRun -x test", path, environmentVariables)
}
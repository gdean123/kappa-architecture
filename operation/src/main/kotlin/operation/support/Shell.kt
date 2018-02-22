package operation.support

import java.io.File

object Shell {
    fun execute(command: String, workingDirectory: File, environmentVariables: Map<String, String> = emptyMap()) {
        val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray())
            .directory(workingDirectory).inheritIO()

        processBuilder.environment().putAll(environmentVariables)
        processBuilder.start().waitFor()
    }
}

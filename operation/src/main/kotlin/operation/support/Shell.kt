package operation.support

import java.io.File

object Shell {
    fun execute(command: String, workingDirectory: File = Paths.root(), environmentVariables: Map<String, String> = emptyMap()) {
        execute(command.split(" "), workingDirectory, environmentVariables)
    }

    fun execute(command: List<String>, workingDirectory: File = Paths.root(), environmentVariables: Map<String, String> = emptyMap()) {
        Log.info("[${workingDirectory.name}] Executing: ${command.joinToString(" ")}")

        val processBuilder = ProcessBuilder(command)
            .directory(workingDirectory).inheritIO()

        processBuilder.environment().putAll(environmentVariables)
        processBuilder.start().waitFor()

        Log.newline()
    }

    fun capture(command: List<String>, workingDirectory: File = Paths.root()): String {
        return ProcessBuilder(command)
            .directory(workingDirectory)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().inputStream.bufferedReader().readText()
    }
}

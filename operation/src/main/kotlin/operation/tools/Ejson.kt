package operation.tools

import operation.support.Paths
import operation.support.Shell
import java.io.File

object Ejson {
    fun decrypt(application: String, environment: String, configuration: String): String =
        Shell.capture(listOf("ejson", "decrypt", "$application/$environment/$configuration.ejson"), Paths.configuration())

    fun encrypt(configurationFile: File) {
        val relativePath = configurationFile.relativeTo(Paths.configuration()).path
        Shell.execute(listOf("ejson", "encrypt", relativePath), Paths.configuration())
    }
}
package operation.domain.actions

import operation.domain.values.Configuration
import operation.support.Environment
import operation.support.Log
import operation.support.Paths
import operation.tools.Ejson
import org.apache.commons.io.FilenameUtils
import java.io.File

object ManageConfigurations {
    fun showAll() {
        configurations().forEach { (application, environment, target) ->
            Log.info("$application / $environment / $target")

            val environmentVariables = Environment.read(application, environment, target)
            environmentVariables.forEach { (key, value) -> println("$key=$value") }

            Log.newline()
        }
    }

    fun encryptAll() {
        configurations()
            .map { configuration -> configuration.file }
            .forEach { configurationFile -> Ejson.encrypt(configurationFile) }

        Log.success("Configuration files encrypted.")
    }

    private fun applications() = Paths.configuration().list()

    private fun configurations(): List<Configuration> {
        return applications().flatMap { application ->
            val environments = File(Paths.configuration(), application).list()

            environments.flatMap { environment ->
                val filenames = File(File(Paths.configuration(), application), environment).list()

                filenames.map { filename ->
                    val target = FilenameUtils.removeExtension(filename)
                    val file = File(File(File(Paths.configuration(), application), environment), filename)

                    Configuration(application, environment, target, file)
                }
            }
        }
    }
}

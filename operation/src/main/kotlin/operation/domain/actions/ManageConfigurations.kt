package operation.domain.actions

import operation.domain.values.Configuration
import operation.support.Environment
import operation.support.Log
import operation.support.Paths
import org.apache.commons.io.FilenameUtils
import java.io.File

object ManageConfigurations {
    fun showAll() {
        configurations().forEach { configuration ->
            Log.info("${configuration.application} / ${configuration.environment} / ${configuration.target}")
            configuration.environmentVariables.forEach { (key, value) -> println("$key = $value") }
            Log.newline()
        }
    }

    private fun applications() = Paths.configuration().list()

    private fun configurations(): List<Configuration> {
        return applications().flatMap { application ->
            val environments = File(Paths.configuration(), application).list()

            environments.flatMap { environment ->
                val filenames = File(File(Paths.configuration(), application), environment).list()
                val targets = filenames.map { configuration -> FilenameUtils.removeExtension(configuration) }

                targets.map { target ->
                    val environmentVariables = Environment.read(application, environment, target)
                    Configuration(application, environment, target, environmentVariables)
                }
            }
        }
    }
}

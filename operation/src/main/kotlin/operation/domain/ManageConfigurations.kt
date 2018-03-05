package operation.domain

import operation.support.Environment
import operation.support.Log
import operation.support.Paths
import org.apache.commons.io.FilenameUtils
import java.io.File

object ManageConfigurations {
    fun showAll() {
        val applications = Paths.configuration().list()
        applications.forEach { application -> printApplication(application) }
    }

    private fun printApplication(application: String) {
        Log.info("--- $application ---")

        val environments = File(Paths.configuration(), application).list()

        environments.forEach { environment ->
            printEnvironment(application, environment)
        }
    }

    private fun printEnvironment(application: String, environment: String) {
        val filenames = File(File(Paths.configuration(), application), environment).list()
        val configurations = filenames.map { configuration -> FilenameUtils.removeExtension(configuration) }

        configurations.forEach { configuration ->
            printConfiguration(environment, configuration, application)
        }
    }

    private fun printConfiguration(environment: String, configuration: String, application: String) {
        Log.info("$environment ($configuration)")
        val environmentVariables = Environment.read(application, environment, configuration)

        environmentVariables.forEach { (key, value) ->
            println("$key = $value")
        }

        Log.newline()
    }
}

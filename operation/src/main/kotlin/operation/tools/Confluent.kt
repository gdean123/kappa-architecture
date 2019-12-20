package operation.tools

import operation.support.Paths
import operation.support.Shell

object Confluent {
    fun start() = confluent("local start")

    fun destroy() = confluent("local destroy")

    fun unload(connector: String) = confluent("local unload $connector")

    fun load(connector: String, configurationFile: String) =
        confluent("local load $connector -d ${Paths.root()}/$configurationFile")

    private fun confluent(command: String) = Shell.execute(
        "confluent $command", Paths.root(),
        mapOf("CONFLUENT_HOME" to Paths.runtime().absolutePath))
}
package operation.tools

import operation.support.Paths
import operation.support.Shell

object Confluent {
    fun start() = Shell.execute("confluent start")

    fun destroy() = Shell.execute("confluent destroy")

    fun unload(connector: String) = Shell.execute("confluent unload $connector")

    fun load(connector: String, configurationFile: String) =
        Shell.execute("confluent load $connector -d ${Paths.root()}/$configurationFile")
}
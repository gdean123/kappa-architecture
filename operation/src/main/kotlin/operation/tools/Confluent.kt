package operation.tools

import operation.support.Shell

object Confluent {
    fun start() = Shell.execute("confluent start")
    fun destroy() = Shell.execute("confluent destroy")
}
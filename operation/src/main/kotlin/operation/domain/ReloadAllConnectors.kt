package operation.domain

import operation.support.Log
import operation.tools.Confluent

object ReloadAllConnectors {
    fun execute() {
        Confluent.unload("jdbc-sink")
        Log.newline()
        Confluent.load("jdbc-sink", "connect/postgres-sink.properties")
    }
}
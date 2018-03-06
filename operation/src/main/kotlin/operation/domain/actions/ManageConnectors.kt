package operation.domain.actions

import operation.support.Log
import operation.tools.Confluent

object ManageConnectors {
    fun load() {
        Confluent.load("jdbc-sink", "connect/postgres-sink.properties")
    }

    fun unload() {
        Confluent.unload("jdbc-sink")
    }

    fun reload() {
        unload()
        Log.newline()
        load()
    }
}
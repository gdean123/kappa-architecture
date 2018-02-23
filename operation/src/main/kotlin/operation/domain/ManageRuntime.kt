package operation.domain

import operation.tools.Confluent

object ManageRuntime {
    fun start() = Confluent.start()

    fun reset() {
        Confluent.destroy()
        Confluent.start()
    }
}
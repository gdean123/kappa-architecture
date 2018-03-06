package operation.user_interface

import operation.domain.actions.ManageConnectors
import picocli.CommandLine.Command

@Command(
    name = "connectors",
    description = ["Manage Kafka Connect connectors"],
    subcommands = [
        Connectors.Load::class,
        Connectors.Unload::class,
        Connectors.Reload::class
    ]
)
class Connectors : Group() {
    @Command(name = "load", description = ["Load all connectors"])
    class Load: Runnable {
        override fun run() = ManageConnectors.load()
    }

    @Command(name = "unload", description = ["Unload all connectors"])
    class Unload: Runnable {
        override fun run() = ManageConnectors.unload()
    }

    @Command(name = "reload", description = ["Unload and reload all connectors"])
    class Reload: Runnable {
        override fun run() = ManageConnectors.reload()
    }
}
package operation.user_interface

import operation.domain.ReloadAllConnectors
import picocli.CommandLine.Command

@Command(
    name = "connectors",
    description = ["Manage Kafka Connect connectors"],
    subcommands = [
        Connectors.Reload::class
    ]
)
class Connectors : Group() {
    @Command(name = "reload", description = ["Load all connectors"])
    class Reload: Runnable {
        override fun run() = ReloadAllConnectors.execute()
    }
}
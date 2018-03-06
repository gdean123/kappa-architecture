package operation.user_interface

import operation.domain.actions.ManageRuntime
import picocli.CommandLine.Command

@Command(
    name = "runtime",
    description = ["Manage runtime (zookeeper, kafka, schema-registry, and connect)"],
    subcommands = [Runtime.Start::class, Runtime.Reset::class]
)
class Runtime : Group() {
    @Command(name = "start", description = ["Start runtime"])
    class Start: Runnable {
        override fun run() = ManageRuntime.start()
    }

    @Command(name = "reset", description = ["Destroy and restart runtime"])
    class Reset: Runnable {
        override fun run() = ManageRuntime.reset()
    }
}
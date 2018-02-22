package operation.user_interface

import operation.domain.ManageTopics
import picocli.CommandLine.Command
import picocli.CommandLine.usage

@Command(
    name = "topics",
    description = ["Manage Kafka topics"],
    subcommands = [
        Topics.List::class,
        Topics.Create::class,
        Topics.Destroy::class
    ],
    footer = [""]
)
class Topics : Runnable {
    override fun run() = usage(this, System.err)

    @Command(name = "list", description = ["List Kafka topics"])
    class List: Runnable {
        override fun run() = ManageTopics.list()
    }

    @Command(name = "create", description = ["Create Kafka topics"])
    class Create: Runnable {
        override fun run() = ManageTopics.create()
    }

    @Command(name = "destroy", description = ["Destroy Kafka topics"])
    class Destroy: Runnable {
        override fun run() = ManageTopics.destroy()
    }
}
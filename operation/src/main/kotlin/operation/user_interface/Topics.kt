package operation.user_interface

import operation.domain.ManageTopics
import picocli.CommandLine.*

@Command(
    name = "topics",
    description = ["Manage Kafka topics"],
    subcommands = [
        Topics.List::class,
        Topics.Create::class,
        Topics.Destroy::class,
        Topics.Publish::class,
        Topics.Subscribe::class
    ],
    footer = [""]
)
class Topics : Runnable {
    override fun run() = usage(this, System.err)

    @Command(name = "list", description = ["List all Kafka topics"])
    class List: Runnable {
        override fun run() = ManageTopics.list()
    }

    @Command(name = "create", description = ["Create all Kafka topics"])
    class Create: Runnable {
        override fun run() = ManageTopics.create()
    }

    @Command(name = "destroy", description = ["Destroy all Kafka topics"])
    class Destroy: Runnable {
        override fun run() = ManageTopics.destroy()
    }

    @Command(name = "publish", description = ["Publish to the streams-plaintext-input Kafka topic"])
    class Publish: Runnable {
        override fun run() = ManageTopics.publish()
    }

    @Command(name = "subscribe", description = ["Subscribe to the streams-wordcount-output Kafka topic"])
    class Subscribe: Runnable {
        override fun run() = ManageTopics.subscribe()
    }
}
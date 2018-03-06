package operation.user_interface

import operation.domain.actions.ManageTopics
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
    ]
)
class Topics: Group() {
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

    @Command(name = "publish", description = ["Publish to the sentence_created Kafka topic"])
    class Publish: Runnable {
        override fun run() = ManageTopics.publish()
    }

    @Command(name = "subscribe", description = ["Subscribe to a Kafka topic"])
    class Subscribe: Runnable {
        @Parameters(index = "0") var topic: String? = null
        override fun run() = ManageTopics.subscribe(topic!!)
    }
}
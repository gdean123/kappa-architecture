package operation.user_interface

import operation.domain.StartProducer
import picocli.CommandLine.*

@Command(
    name = "start",
    description = ["Start services"],
    subcommands = [Start.Producer::class],
    footer = [""]
)
class Start : Runnable {
    override fun run() = usage(this, System.err)

    @Command(name = "producer", description = ["Start the producer"])
    class Producer: Runnable {
        override fun run() = StartProducer.execute()
    }
}
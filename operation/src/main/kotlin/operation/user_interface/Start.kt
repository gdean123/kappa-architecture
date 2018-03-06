package operation.user_interface

import operation.domain.actions.StartService
import picocli.CommandLine.*

@Command(
    name = "start",
    description = ["Start services"],
    subcommands = [Start.Producer::class, Start.StreamProcessor::class, Start.Consumer::class]
)
class Start: Group() {
    @Command(name = "producer", description = ["Start the producer"])
    class Producer: Runnable {
        override fun run() = StartService.producer()
    }

    @Command(name = "stream-processor", description = ["Start the stream processor"])
    class StreamProcessor: Runnable {
        override fun run() = StartService.streamProcessor()
    }

    @Command(name = "consumer", description = ["Start the consumer"])
    class Consumer: Runnable {
        override fun run() = StartService.consumer()
    }
}
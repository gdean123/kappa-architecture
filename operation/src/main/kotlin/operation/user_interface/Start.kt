package operation.user_interface

import operation.domain.StartApplication
import picocli.CommandLine.*

@Command(
    name = "start",
    description = ["Start services"],
    subcommands = [
        Start.Producer::class,
        Start.StreamProcessor::class
    ]
)
class Start: Group() {
    @Command(name = "producer", description = ["Start the producer"])
    class Producer: Runnable {
        override fun run() = StartApplication.producer()
    }

    @Command(name = "stream-processor", description = ["Start the stream processor"])
    class StreamProcessor: Runnable {
        override fun run() = StartApplication.streamProcessor()
    }
}
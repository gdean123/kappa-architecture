package operation.user_interface

import operation.domain.actions.RunTests
import operation.domain.actions.StartService
import picocli.CommandLine

@CommandLine.Command(
    name = "test",
    description = ["Run tests"],
    subcommands = [Test.Producer::class, Test.Consumer::class]
)
class Test : Group() {
    @CommandLine.Command(name = "producer", description = ["Run the producer tests"])
    class Producer: Runnable {
        override fun run() = RunTests.producer()
    }

    @CommandLine.Command(name = "consumer", description = ["Run the consumer tests"])
    class Consumer: Runnable {
        override fun run() = RunTests.consumer()
    }
}
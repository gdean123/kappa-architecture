package operation.user_interface

import operation.domain.RunTests
import operation.domain.StartProducer
import picocli.CommandLine.*

@Command(
    name = "kappa",
    header = ["%nkappa command line interface%n"],
    footer = [""],
    subcommands = [Kappa.Test::class, Kappa.Start::class]
)
class Kappa : Runnable {
    @Option(names = ["-h", "--help"], usageHelp = true, description = ["Print usage help and exit."])
    var usageHelpRequested: Boolean = true

    override fun run() {
        if (usageHelpRequested) {
            usage(this, System.err)
        }
    }

    @Command(name = "test", description = ["Run all tests"])
    class Test : Runnable {
        override fun run() = RunTests.execute()
    }

    @Command(
        name = "start",
        description = ["Start services"],
        subcommands = [Kappa.Start.Producer::class],
        footer = [""]
    )
    class Start : Runnable {
        override fun run() = usage(this, System.err)

        @Command(name = "producer", description = ["Start the producer"])
        class Producer: Runnable {
            override fun run() = StartProducer.execute()
        }
    }
}

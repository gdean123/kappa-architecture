package operation.user_interface

import operation.domain.RunTests
import picocli.CommandLine.*

@Command(
    name = "kappa",
    header = ["%nkappa command line interface%n"],
    footer = [""],
    subcommands = [
        Kappa.Test::class,
        Start::class,
        Topics::class
    ]
)
class Kappa : Runnable {
    override fun run() = usage(this, System.err)

    @Command(name = "test", description = ["Run all tests"])
    class Test : Runnable {
        override fun run() = RunTests.producer()
    }
}

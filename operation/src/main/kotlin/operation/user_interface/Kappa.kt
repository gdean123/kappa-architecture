package operation.user_interface

import operation.domain.RunTests
import picocli.CommandLine.*

@Command(
    name = "kappa",
    header = ["%nkappa command line interface%n"],
    subcommands = [
        Kappa.Test::class,
        Start::class,
        Topics::class,
        Runtime::class
    ]
)
class Kappa: Group() {
    @Command(name = "test", description = ["Run all tests"])
    class Test : Runnable {
        override fun run() = RunTests.producer()
    }
}

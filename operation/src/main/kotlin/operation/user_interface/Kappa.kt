package operation.user_interface

import operation.domain.BuildArtifact
import operation.domain.RunTests
import picocli.CommandLine.Command

@Command(
    name = "kappa",
    header = ["%nkappa command line interface%n"],
    subcommands = [
        Kappa.Build::class,
        Kappa.Test::class,
        Start::class,
        Topics::class,
        Connectors::class,
        Runtime::class,
        Bindings::class
    ]
)
class Kappa: Group() {
    @Command(name = "build", description = ["Build all artifacts"])
    class Build : Runnable {
        override fun run() = BuildArtifact.all()
    }

    @Command(name = "test", description = ["Run all tests"])
    class Test : Runnable {
        override fun run() = RunTests.producer()
    }
}

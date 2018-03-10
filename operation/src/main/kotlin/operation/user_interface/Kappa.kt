package operation.user_interface

import operation.domain.actions.BuildArtifact
import operation.domain.actions.RunTests
import picocli.CommandLine.Command

@Command(
    name = "kappa",
    header = ["%nkappa command line interface%n"],
    subcommands = [
        Kappa.Build::class,
        Start::class,
        Test::class,
        Topics::class,
        Connectors::class,
        Runtime::class,
        Bindings::class,
        Configurations::class
    ]
)
class Kappa: Group() {
    @Command(name = "build", description = ["Build all artifacts"])
    class Build : Runnable {
        override fun run() = BuildArtifact.all()
    }
}

package operation.user_interface

import operation.domain.actions.ManageBindings
import picocli.CommandLine.Command

@Command(
    name = "bindings",
    description = ["Manage Avro bindings"],
    subcommands = [
        Bindings.Generate::class,
        Bindings.Clean::class
    ]
)
class Bindings : Group() {
    @Command(name = "generate", description = ["Generate Avro/Java bindings for all services"])
    class Generate: Runnable {
        override fun run() = ManageBindings.generate()
    }

    @Command(name = "clean", description = ["Delete Avro/Java bindings for all services"])
    class Clean: Runnable {
        override fun run() = ManageBindings.clean()
    }
}
package operation.user_interface

import operation.domain.actions.ManageConfigurations
import picocli.CommandLine.Command

@Command(
    name = "configurations",
    description = ["Manage configuration data"],
    subcommands = [
        Configurations.Show::class,
        Configurations.Encrypt::class
    ]
)
class Configurations : Group() {
    @Command(name = "show", description = ["Show all configurations"])
    class Show: Runnable {
        override fun run() = ManageConfigurations.showAll()
    }

    @Command(name = "encrypt", description = ["Encrypt all configurations"])
    class Encrypt: Runnable {
        override fun run() = ManageConfigurations.encryptAll()
    }
}
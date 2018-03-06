package operation.user_interface

import operation.domain.actions.ManageConfigurations
import picocli.CommandLine.Command

@Command(
    name = "configurations",
    description = ["Manage configuration data"],
    subcommands = [
        Configurations.Show::class
    ]
)
class Configurations : Group() {
    @Command(name = "show", description = ["Show configuration for all applications and environments"])
    class Show: Runnable {
        override fun run() = ManageConfigurations.showAll()
    }
}
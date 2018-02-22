package operation.user_interface

import picocli.CommandLine.*

@Command(
    footer = [""]
)
open class Group : Runnable {
    override fun run() = usage(this, System.err)
}
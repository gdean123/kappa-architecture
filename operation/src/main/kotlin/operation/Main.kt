package operation

import operation.user_interface.Kappa
import picocli.CommandLine
import picocli.CommandLine.RunLast

fun main(arguments: Array<String>) {
    CommandLine(Kappa())
        .parseWithHandler(RunLast(), System.err, *arguments)
}

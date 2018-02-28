package operation.support

object Log {
    private val ANSI_RESET = "\u001B[0m"
    private val ANSI_YELLOW = "\u001B[33m"
    private val ANSI_GREEN = "\u001B[32m"

    fun info(message: String) = println("$ANSI_YELLOW$message$ANSI_RESET\n")
    fun success(message: String) = println("$ANSI_GREEN$message$ANSI_RESET\n")
    fun newline() = println()
}

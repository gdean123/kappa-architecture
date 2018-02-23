package operation.support

object Log {
    private val ANSI_RESET = "\u001B[0m"
    private val ANSI_YELLOW = "\u001B[33m"

    fun info(message: String) {
        println("$ANSI_YELLOW$message$ANSI_RESET\n")
    }
}

package operation.tools

import operation.support.Paths
import operation.support.Shell

object Ejson {
    fun decrypt(application: String, environment: String, type: String): String =
        Shell.capture(listOf("ejson", "decrypt", "$application/$environment/$type.ejson"), Paths.configuration())
}
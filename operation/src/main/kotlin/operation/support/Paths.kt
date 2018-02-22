package operation.support

import java.io.File

object Paths {
    fun producer() = File(root(), "producer")

    private fun root() = jarFile().parentFile.parentFile.parentFile.parentFile
    private fun jarFile() = File(this::class.java.protectionDomain.codeSource.location.toURI().path)
}

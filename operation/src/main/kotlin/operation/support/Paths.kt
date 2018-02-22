package operation.support

import java.io.File

object Paths {
    fun producer() = path("producer")
    fun streamProcessor() = path("stream-processor")

    private fun path(path: String) = File(root(), path)
    private fun root() = jarFile().parentFile.parentFile.parentFile.parentFile
    private fun jarFile() = File(this::class.java.protectionDomain.codeSource.location.toURI().path)
}

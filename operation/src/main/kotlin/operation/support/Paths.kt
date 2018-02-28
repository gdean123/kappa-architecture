package operation.support

import java.io.File

object Paths {
    fun root(): File = jarFile().parentFile.parentFile.parentFile.parentFile

    fun producer() = path("producer")
    fun producerContracts() = path("producer/contracts")
    fun producerGen() = path("producer/src/gen/java")

    fun streamProcessor() = path("stream-processor")
    fun streamProcessorContracts() = path("stream-processor/contracts")
    fun streamProcessorGen() = path("stream-processor/src/gen/java")

    private fun path(path: String) = File(root(), path)
    private fun jarFile() = File(this::class.java.protectionDomain.codeSource.location.toURI().path)
}

package operation.support

import java.io.File

object Paths {
    fun root(): File = jarFile().parentFile.parentFile.parentFile.parentFile
    fun configuration() = path("configuration")

    fun producer() = path("producer")
    fun producerContracts() = path("producer/contracts")
    fun producerGen() = path("producer/src/gen/java")

    fun streamProcessor() = path("stream-processor")
    fun streamProcessorContracts() = path("stream-processor/contracts")
    fun streamProcessorGen() = path("stream-processor/src/gen/java")

    fun consumer() = path("consumer")
    fun consumerContracts() = path("consumer/contracts")
    fun consumerGen() = path("consumer/src/gen/java")

    fun acceptanceTests() = path("acceptance-tests")

    private fun path(path: String) = File(root(), path)
    private fun jarFile() = File(this::class.java.protectionDomain.codeSource.location.toURI().path)
}

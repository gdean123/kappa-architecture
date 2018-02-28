package operation.domain

import operation.support.Log
import operation.support.Paths
import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import org.apache.avro.generic.GenericData
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors.toList

object ManageBindings {
    fun generate() {
        Log.info("Generating schema bindings")

        generateBindings(Paths.producerContracts(), Paths.producerGen())
        generateBindings(Paths.streamProcessorContracts(), Paths.streamProcessorGen())

        Log.success("Schema bindings generated")
    }

    fun clean() {
        Paths.producerGen().deleteRecursively()
        Paths.streamProcessorGen().deleteRecursively()

        Log.info("Schema bindings deleted")
    }

    private fun generateBindings(contracts: File, outputDirectory: File) {
        val files = getFiles(contracts, "*.avsc")
        files.forEach({ file -> generateBindingsForFile(file, outputDirectory) })
    }

    private fun getFiles(directory: File, globPattern: String): List<File> {
        val matcher = FileSystems.getDefault().getPathMatcher("glob:$globPattern")
        val path = java.nio.file.Paths.get(directory.toString())

        return Files.walk(path)
            .filter { it: Path? -> it?.let { matcher.matches(it.fileName) } ?: false }
            .map { currentPath -> currentPath.toFile() }
            .collect(toList())
    }

    private fun generateBindingsForFile(file: File, outputDirectory: File) {
        val schema = Schema.Parser().parse(file)
        val compiler = SpecificCompiler(schema)
        compiler.setStringType(GenericData.StringType.String)

        compiler.compileToDestination(file, outputDirectory)
    }
}
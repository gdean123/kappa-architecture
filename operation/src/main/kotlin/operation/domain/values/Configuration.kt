package operation.domain.values

import java.io.File

data class Configuration(
    val application: String,
    val environment: String,
    val target: String,
    val file: File,
    val environmentVariables: Map<String, String>
)
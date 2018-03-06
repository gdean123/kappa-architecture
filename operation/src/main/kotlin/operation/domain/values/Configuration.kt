package operation.domain.values

data class Configuration(
    val application: String,
    val environment: String,
    val target: String,
    val environmentVariables: Map<String, String>
)
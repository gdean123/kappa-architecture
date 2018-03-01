package stream_processor

object Environment {
    fun applicationId(): String = System.getenv("APPLICATION_ID")
    fun kakfaUrl(): String = System.getenv("KAFKA_URL")
    fun schemaRegistryUrl(): String = System.getenv("SCHEMA_REGISTRY_URL")
}
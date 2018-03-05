package operation.support

import operation.tools.Ejson

object Environment {
    fun read(application: String, environment: String, configuration: String): Map<String, String> {
        val output = Ejson.decrypt(application, environment, configuration)
        return Json.parseMap(output).filterKeys { key -> !key.startsWith("_") }
    }
}

package operation.support

import operation.tools.Ejson

object Environment {
    fun read(application: String): Map<String, String> {
        val output = Ejson.decrypt(application, "development", "application")
        return Json.parseMap(output).filterKeys { key -> !key.startsWith("_") }
    }
}

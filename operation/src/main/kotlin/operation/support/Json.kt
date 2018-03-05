package operation.support

import org.codehaus.jackson.map.ObjectMapper

object Json {
    fun parseMap(jsonString: String): Map<String, String> {
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(jsonString)
        val mapType = objectMapper.typeFactory.constructMapType(Map::class.java, String::class.java, String::class.java)

        return objectMapper.convertValue(jsonNode, mapType)
    }
}
package com.kappa.acceptance_tests.support

import org.apache.http.HttpResponse
import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType

object Http {
    inline fun <reified T: Any> get(url: String): JsonResponse<T> {
        val response = Request.Get(url).execute().returnResponse()
        return JsonResponse(response.statusLine.statusCode, Json.parse(response))
    }

    fun post(url: String, body: Any): HttpResponse =
        Request.Post(url)
            .bodyString(Json.write(body), ContentType.APPLICATION_JSON)
            .execute().returnResponse()
}
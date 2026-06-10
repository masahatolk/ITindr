package com.hits.itindr.network

import com.hits.itindr.auth.TokenStore
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiHttpClient(
    private val tokenStore: TokenStore,
) {
    suspend fun get(path: String, authorized: Boolean = true): ApiResponse = request(
        path = path,
        method = METHOD_GET,
        body = null,
        authorized = authorized,
    )

    suspend fun post(
        path: String,
        body: String? = EMPTY_JSON,
        authorized: Boolean = true,
    ): ApiResponse = request(
        path = path,
        method = METHOD_POST,
        body = body,
        authorized = authorized,
    )

    private suspend fun request(
        path: String,
        method: String,
        body: String?,
        authorized: Boolean,
    ): ApiResponse = withContext(Dispatchers.IO) {
        val connection = (URL(BASE_URL + path).openConnection() as HttpURLConnection).apply {
            requestMethod = method
            connectTimeout = TIMEOUT_MS
            readTimeout = TIMEOUT_MS
            setRequestProperty(HEADER_ACCEPT, CONTENT_TYPE_JSON)
            setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
            if (authorized) {
                tokenStore.getToken()?.let { token ->
                    setRequestProperty(HEADER_AUTHORIZATION, "Bearer $token")
                }
            }
            if (body != null) {
                doOutput = true
            }
        }

        try {
            if (body != null) {
                connection.outputStream.use { output ->
                    output.write(body.encodeToByteArray())
                }
            }

            val statusCode = connection.responseCode
            val responseBody = readBody(connection, statusCode)
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                tokenStore.clearToken()
            }
            ApiResponse(statusCode, responseBody)
        } finally {
            connection.disconnect()
        }
    }

    private fun readBody(connection: HttpURLConnection, statusCode: Int): String {

        if (statusCode == HttpURLConnection.HTTP_NO_CONTENT) {
            return ""
        }

        val stream = if (statusCode in SUCCESS_CODES) {
            connection.inputStream
        } else {
            connection.errorStream ?: connection.inputStream
        }

        return stream.bufferedReader().use(BufferedReader::readText)
    }

    private companion object {
        const val BASE_URL = "http://158.160.26.231:18081/itindr/api/mobile/v1"
        const val TIMEOUT_MS = 15_000
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
        const val EMPTY_JSON = "{}"
        const val HEADER_ACCEPT = "Accept"
        const val HEADER_CONTENT_TYPE = "Content-Type"
        const val HEADER_AUTHORIZATION = "Authorization"
        const val CONTENT_TYPE_JSON = "application/json"
        val SUCCESS_CODES = 200..299
    }
}

data class ApiResponse(
    val statusCode: Int,
    val body: String,
) {
    val isSuccessful: Boolean = statusCode in 200..299
}

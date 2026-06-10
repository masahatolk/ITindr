package com.hits.itindr.network

import android.content.ContentValues.TAG
import android.util.Log
import com.hits.itindr.auth.TokenStore
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor

class ApiHttpClient(
    private val tokenStore: TokenStore,
) {
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(createLoggingInterceptor())
        .build()

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
        val request = buildRequest(path, method, body, authorized)

        try {
            httpClient.newCall(request).execute().use { response ->
                val responseBody = response.body?.string().orEmpty()
                if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    tokenStore.clearToken()
                }
                ApiResponse(response.code, responseBody)
            }

        } catch (exception: IOException) {
            Log.e(TAG, "Request failed: ${exception.message}", exception)
            throw exception
        }
    }

    private fun buildRequest(
        path: String,
        method: String,
        body: String?,
        authorized: Boolean,
    ): Request {
        val requestBuilder = Request.Builder()
            .url(BASE_URL + path)
            .header(HEADER_ACCEPT, CONTENT_TYPE_JSON)
            .header(HEADER_USER_AGENT, USER_AGENT)

        if (authorized) {
            tokenStore.getToken()?.let { token ->
                requestBuilder.header(HEADER_AUTHORIZATION, "Bearer $token")
            }
        }

        return when (method) {
            METHOD_GET -> requestBuilder.get().build()
            METHOD_POST -> requestBuilder
                .post((body ?: EMPTY_BODY).toRequestBody(JSON_MEDIA_TYPE))
                .build()
            else -> requestBuilder.method(method, body?.toRequestBody(JSON_MEDIA_TYPE)).build()
        }
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d(TAG, message.sanitizeJsonForLogs())
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
            redactHeader(HEADER_AUTHORIZATION)
        }
    }

    private fun String.sanitizeJsonForLogs(): String {
        return runCatching {
            sanitizeJsonElement(json.parseToJsonElement(this)).toString()
        }.getOrDefault(this)
    }

    private fun sanitizeJsonElement(element: JsonElement): JsonElement {
        return when (element) {
            is JsonObject -> buildJsonObject {
                element.forEach { (key, value) ->
                    val sanitizedValue = if (key.lowercase() in SENSITIVE_KEYS) {
                        REDACTED_JSON_STRING
                    } else {
                        sanitizeJsonElement(value)
                    }
                    put(key, sanitizedValue)
                }
            }
            else -> element
        }
    }

    private companion object {
        const val BASE_URL = "http://158.160.26.231:18080/itindr/api/mobile/v1"
        const val TIMEOUT_SECONDS = 15L
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
        const val EMPTY_JSON = "{}"
        const val EMPTY_BODY = ""
        const val HEADER_ACCEPT = "Accept"
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_USER_AGENT = "User-Agent"
        const val CONTENT_TYPE_JSON = "application/json"
        const val USER_AGENT = "ITindr-Android"
        val JSON_MEDIA_TYPE = CONTENT_TYPE_JSON.toMediaType()
        val SENSITIVE_KEYS = setOf(
            "password",
            "passwordconfirm",
            "token",
            "accesstoken",
            "access_token",
            "jwt",
        )
        val REDACTED_JSON_STRING = JsonPrimitive("***")
        val json = Json { ignoreUnknownKeys = true }
    }
}

data class ApiResponse(
    val statusCode: Int,
    val body: String,
) {
    val isSuccessful: Boolean = statusCode in 200..299
}

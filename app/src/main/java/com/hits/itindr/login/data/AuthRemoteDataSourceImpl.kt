package com.hits.itindr.login.data

import com.hits.itindr.network.ApiException
import com.hits.itindr.network.ApiHttpClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.put

class AuthRemoteDataSourceImpl (
    private val httpClient: ApiHttpClient,
) : AuthRemoteDataSource {
    override suspend fun login(email: String, password: String): String {
        val requestBody = buildJsonObject {
            put("email", email)
            put("username", email)
            put("password", password)
        }.toString()

        val response = httpClient.post(LOGIN_PATH, requestBody, authorized = false)

        if (response.isSuccessful) {
            return parseToken(response.body)
        }

        throw ApiException(response.statusCode, response.body)
    }

    private fun parseToken(responseBody: String): String {
        val root = json.parseToJsonElement(responseBody)
        TOKEN_KEYS.firstNotNullOfOrNull { key -> root.findString(key) }?.let { return it }
        throw ApiException(0, "Сервер не вернул токен авторизации")
    }

    private fun JsonElement.findString(key: String): String? {
        val currentObject = this as? JsonObject ?: return null
        (currentObject[key] as? JsonPrimitive)
            ?.contentOrNull
            ?.takeIf(String::isNotBlank)
            ?.let { return it }
        return currentObject.values.firstNotNullOfOrNull { child -> child.findString(key) }
    }

    private companion object {
        const val NOT_FOUND = 404
        const val LOGIN_PATH = "/v1/auth/login"
        val TOKEN_KEYS = listOf("accessToken", "access_token", "token", "jwt")
        val json = Json { ignoreUnknownKeys = true }
    }
}
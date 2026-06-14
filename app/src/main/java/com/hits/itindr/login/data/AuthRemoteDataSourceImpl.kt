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
        val requestBody = buildAuthRequestBody(email, password)

        val response = httpClient.post(LOGIN_PATH, requestBody, authorized = false)

        if (response.isSuccessful) {
            return parseToken(response.body)
        }

        throw ApiException(response.statusCode, response.body)
    }

    override suspend fun register(email: String, password: String): String {
        val requestBody = buildAuthRequestBody(email, password)
        val response = httpClient.post(REGISTER_PATH, requestBody, authorized = false)

        if (response.isSuccessful) {
            parseTokenOrNull(response.body)?.let { token -> return token }
            return login(email, password)
        }

        throw ApiException(response.statusCode, response.body)
    }

    override suspend fun logout() {
        val response = httpClient.delete(
            path = LOGOUT_PATH,
            authorized = true
        )

        if (!response.isSuccessful) {
            throw ApiException(response.statusCode, response.body)
        }
    }

    private fun buildAuthRequestBody(email: String, password: String): String {
        return buildJsonObject {
            put("email", email)
            put("password", password)
        }.toString()
    }

    private fun parseToken(responseBody: String): String {
        return parseTokenOrNull(responseBody)
            ?: throw ApiException(0, "Сервер не вернул токен авторизации")
    }

    private fun parseTokenOrNull(responseBody: String): String? {
        return runCatching {
            val root = json.parseToJsonElement(responseBody)
            (root as? JsonPrimitive)
                ?.contentOrNull
                ?.takeIf(String::isNotBlank)
                ?: TOKEN_KEYS.firstNotNullOfOrNull { key -> root.findString(key) }
        }.getOrNull()
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
        const val LOGIN_PATH = "/auth/login"
        const val REGISTER_PATH = "/auth/register"
        const val LOGOUT_PATH = "/auth/logout"
        val TOKEN_KEYS = listOf("accessToken", "access_token", "token", "jwt")
        val json = Json { ignoreUnknownKeys = true }
    }
}
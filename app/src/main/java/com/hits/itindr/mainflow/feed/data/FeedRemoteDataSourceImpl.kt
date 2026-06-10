package com.hits.itindr.mainflow.feed.data

import com.hits.itindr.mainflow.feed.domain.ReactionResult
import com.hits.itindr.mainflow.feed.swipeableCards.Profile
import com.hits.itindr.network.ApiException
import com.hits.itindr.network.ApiHttpClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.put

class FeedRemoteDataSourceImpl(
    private val httpClient: ApiHttpClient,
) : FeedRemoteDataSource {
    override suspend fun getProfiles(): List<Profile> {

        val response = httpClient.get(FEED_PATH)
        if (response.isSuccessful) {
            return parseProfiles(response.body)
        }

        throw ApiException(0, "Не удалось загрузить ленту")
    }

    override suspend fun likeProfile(profileId: String): ReactionResult {
        return sendReaction(profileId, LIKE_PATHS)
    }

    override suspend fun dislikeProfile(profileId: String): ReactionResult {
        return sendReaction(profileId, DISLIKE_PATHS)
    }

    private suspend fun sendReaction(profileId: String, pathTemplates: List<String>): ReactionResult {
        var lastError: ApiException? = null
        for (template in pathTemplates) {
            val path = template.replace(PROFILE_ID_PLACEHOLDER, profileId)
            val body = buildJsonObject { put("profileId", profileId) }.toString()
            val response = httpClient.post(path, body)
            if (response.isSuccessful) {
                return ReactionResult(isMutual = parseIsMutual(response.body))
            }
            lastError = ApiException(response.statusCode, response.body)
            if (response.statusCode != NOT_FOUND) break
        }
        throw lastError ?: ApiException(0, "Не удалось отправить реакцию")
    }

    private fun parseProfiles(responseBody: String): List<Profile> {
        val root = json.parseToJsonElement(responseBody)
        val items = when (root) {
            is JsonArray -> root
            is JsonObject -> PROFILE_ARRAY_KEYS.firstNotNullOfOrNull { key ->
                root[key] as? JsonArray
            } ?: JsonArray(emptyList())
            else -> JsonArray(emptyList())
        }

        return items.mapIndexedNotNull { index, element ->
            val profileObject = element as? JsonObject ?: return@mapIndexedNotNull null
            Profile(
                id = profileObject.findString(ID_KEYS) ?: index.toString(),
                name = profileObject.findString(NAME_KEYS).orEmpty(),
                tags = profileObject.findStringList(TAG_KEYS),
                description = profileObject.findString(DESCRIPTION_KEYS).orEmpty(),
                imageResName = DEFAULT_IMAGE_RES_NAME,
                imageUrl = profileObject.findString(IMAGE_KEYS),
            )
        }
    }

    private fun parseIsMutual(responseBody: String): Boolean {
        return runCatching {
            json.parseToJsonElement(responseBody).findBoolean(MUTUAL_KEYS) ?: false
        }.getOrDefault(false)
    }

    private fun JsonElement.findBoolean(keys: List<String>): Boolean? {
        val currentObject = this as? JsonObject ?: return null
        keys.firstNotNullOfOrNull { key ->
            (currentObject[key] as? JsonPrimitive)?.booleanOrNull
        }?.let { return it }
        return currentObject.values.firstNotNullOfOrNull { child -> child.findBoolean(keys) }
    }

    private fun JsonObject.findString(keys: List<String>): String? {
        return keys.firstNotNullOfOrNull { key ->
            (this[key] as? JsonPrimitive)?.contentOrNull?.takeIf(String::isNotBlank)
        }
    }

    private fun JsonObject.findStringList(keys: List<String>): List<String> {
        keys.forEach { key ->
            val value = this[key] ?: return@forEach
            if (value is JsonArray) {
                return value.mapNotNull { item ->
                    (item as? JsonPrimitive)?.contentOrNull?.takeIf(String::isNotBlank)
                }
            }
            (value as? JsonPrimitive)?.contentOrNull
                ?.split(',', ';')
                ?.map(String::trim)
                ?.filter(String::isNotBlank)
                ?.takeIf { it.isNotEmpty() }
                ?.let { return it }
        }
        return emptyList()
    }

    private companion object {
        const val NOT_FOUND = 404
        const val PROFILE_ID_PLACEHOLDER = "{id}"
        const val DEFAULT_IMAGE_RES_NAME = "photo"
        const val FEED_PATH = "/user/feed"
        val LIKE_PATHS = listOf(
            "/api/feed/{id}/like",
            "/feed/{id}/like",
            "/api/profiles/{id}/like",
            "/profiles/{id}/like",
            "/api/feed/like",
            "/feed/like",
            "/api/profiles/like",
            "/profiles/like",
        )
        val DISLIKE_PATHS = listOf(
            "/api/feed/{id}/dislike",
            "/feed/{id}/dislike",
            "/api/profiles/{id}/dislike",
            "/profiles/{id}/dislike",
            "/api/feed/dislike",
            "/feed/dislike",
            "/api/profiles/dislike",
            "/profiles/dislike",
        )
        val PROFILE_ARRAY_KEYS = listOf("items", "profiles", "users", "data", "content")
        val ID_KEYS = listOf("id", "userId", "profileId", "uuid")
        val NAME_KEYS = listOf("name", "fullName", "username", "login")
        val TAG_KEYS = listOf("tags", "interests", "skills", "stack")
        val DESCRIPTION_KEYS = listOf("description", "about", "bio", "additionalInfo", "info")
        val IMAGE_KEYS = listOf("imageUrl", "avatarUrl", "photoUrl", "avatar", "photo")
        val MUTUAL_KEYS = listOf("isMutual", "mutual")
        val json = Json { ignoreUnknownKeys = true }
    }
}
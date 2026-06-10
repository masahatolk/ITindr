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
import kotlinx.serialization.json.contentOrNull

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
        return sendReaction(profileId, LIKE_PATH)
    }

    override suspend fun dislikeProfile(profileId: String): ReactionResult {
        return sendReaction(profileId, DISLIKE_PATH)
    }

    private suspend fun sendReaction(profileId: String, pathTemplate: String): ReactionResult {
        val path = pathTemplate.replace(USER_ID_PLACEHOLDER, profileId)
        val response = httpClient.post(path, body = null)
        if (response.isSuccessful) {
            return ReactionResult(isMutual = parseIsMutual(response.body))
        }
        throw ApiException(response.statusCode, response.body)
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
        const val USER_ID_PLACEHOLDER = "{userId}"
        const val DEFAULT_IMAGE_RES_NAME = "photo"
        const val FEED_PATH = "/user/feed"
        const val LIKE_PATH = "/user/{userId}/like"
        const val DISLIKE_PATH = "/user/{userId}/dislike"
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
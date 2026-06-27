package com.hits.impl.data

import com.hits.api.model.Chat
import com.hits.api.repository.ChatRepository
import com.hits.api.repository.FeedRepository

class LikeProfileUseCase(
    private val feedRepository: FeedRepository,
    private val chatRepository: ChatRepository,
) {

    suspend operator fun invoke(userId: String): LikeProfileResult {

        val result = feedRepository.likeProfile(userId)


        if (result.isMutual) {
            val chat = chatRepository.createChat(userId)

            return LikeProfileResult.Mutual(chat)
        }

        return LikeProfileResult.Success
    }
}

sealed interface LikeProfileResult {

    data object Success : LikeProfileResult

    data class Mutual(
        val chat: Chat
    ) : LikeProfileResult
}
package com.hits.itindr.domain.usecase

import com.hits.api.repository.ChatRepository
import com.hits.itindr.mainflow.feed.LikeProfileResult
import com.hits.itindr.mainflow.feed.domain.FeedRepository

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
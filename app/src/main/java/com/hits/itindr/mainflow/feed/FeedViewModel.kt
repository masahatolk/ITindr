package com.hits.itindr.mainflow.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.itindr.AppGraph
import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.network.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: FeedRepository = AppGraph.feedRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedUiState(isLoading = true))
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        onIntent(FeedIntent.LoadFeed)
    }

    fun onIntent(intent: FeedIntent) {
        when (intent) {
            FeedIntent.LoadFeed -> loadFeed()
            is FeedIntent.Like -> sendLike(intent.profile.id)
            is FeedIntent.Dislike -> sendDislike(intent.profile.id)
            FeedIntent.ErrorShown -> _uiState.update { it.copy(errorMessage = null) }
            FeedIntent.MutualMatchShown -> _uiState.update { it.copy(mutualMatchMessage = null) }
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { repository.getProfiles() }
                .onSuccess { profiles ->
                    _uiState.update { it.copy(profiles = profiles, isLoading = false) }
                }
                .onFailure(::handleError)
        }
    }

    private fun sendLike(profileId: String) {
        viewModelScope.launch {
            runCatching { repository.likeProfile(profileId) }
                .onSuccess { reactionResult ->
                    if (reactionResult.isMutual) {
                        _uiState.update { state ->
                            state.copy(mutualMatchMessage = "Ваши интерфейсы подошли друг другу")
                        }
                    }
                }
                .onFailure(::handleError)
        }
    }

    private fun sendDislike(profileId: String) {
        viewModelScope.launch {
            runCatching { repository.dislikeProfile(profileId) }
                .onFailure(::handleError)
        }
    }

    private fun handleError(throwable: Throwable) {
        val tokenExpired = (throwable as? ApiException)?.statusCode == HTTP_UNAUTHORIZED
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                isTokenExpired = state.isTokenExpired || tokenExpired,
                errorMessage = if (tokenExpired) {
                    "Сессия истекла. Войдите снова"
                } else {
                    throwable.message?.takeIf(String::isNotBlank) ?: "Ошибка сети"
                },
            )
        }
    }

    private companion object {
        const val HTTP_UNAUTHORIZED = 401
    }
}
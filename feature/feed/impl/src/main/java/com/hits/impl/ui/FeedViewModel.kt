package com.hits.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.api.model.User
import com.hits.core_network.ApiException
import com.hits.itindr.domain.usecase.LikeProfileUseCase
import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.match.MatchData
import com.hits.itindr.mainflow.match.MatchStore
import com.hits.itindr.mainflow.profile.data.ProfileRepository
import com.hits.itindr.mainflow.profile.domain.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: FeedRepository,
    private val likeProfileUseCase: LikeProfileUseCase,
    private val profileRepository: ProfileRepository,
    private val matchStore: MatchStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedUiState(isLoading = true))
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()
    private var currentProfile: Profile? = null

    init {
        loadCurrentProfile()
    }

    private fun loadCurrentProfile() {

        viewModelScope.launch {

            runCatching {

                profileRepository.getProfile()

            }.onSuccess {

                currentProfile = it
            }
        }
    }

    fun onIntent(intent: FeedIntent) {
        when (intent) {
            FeedIntent.LoadFeed -> loadFeed()
            is FeedIntent.Like -> sendLike(intent.profile)
            is FeedIntent.Dislike -> sendDislike(intent.profile.id)
            FeedIntent.ErrorShown -> _uiState.update { it.copy(errorMessage = null) }
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

    private fun sendLike(profile: User) {
        viewModelScope.launch {

            runCatching {

                likeProfileUseCase(profile.id)

            }.onSuccess { result ->

                when (result) {

                    is LikeProfileResult.Success -> Unit

                    is LikeProfileResult.Mutual -> {

                        matchStore.showMatch(
                            MatchData(
                                chatId = result.chat.id,
                                chatTitle = result.chat.title,
                                currentUserAvatar = currentProfile?.avatar,
                                matchedUser = profile
                            )
                        )
                    }
                }

            }.onFailure(::handleError)
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
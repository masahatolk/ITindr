package com.hits.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.api.model.MatchData
import com.hits.api.model.Profile
import com.hits.api.model.User
import com.hits.api.repository.FeedRepository
import com.hits.api.repository.ProfileRepository
import com.hits.core_network.ApiException
import com.hits.impl.data.LikeProfileResult
import com.hits.impl.data.LikeProfileUseCase
import com.hits.impl.data.MatchStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val feedRepository: FeedRepository,
    private val profileRepository: ProfileRepository,
    private val likeProfileUseCase: LikeProfileUseCase,
    private val matchStore: MatchStore,
) : ViewModel() {

    private val _state = MutableStateFlow(PeopleUiState())

    val state = _state.asStateFlow()

    private var offset = 0

    private val limit = 20

    private var isEndReached = false

    private var currentProfile: Profile? = null

    init {
        loadNextPage()

        loadCurrentProfile()
    }

    fun loadNextPage() {

        if (_state.value.isLoading) return

        if (isEndReached) return

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            runCatching {

                feedRepository.getAllUsers(
                    limit = limit, offset = offset
                )

            }.onSuccess { users ->

                if (users.isEmpty()) {
                    isEndReached = true
                }

                offset += users.size

                _state.update {
                    it.copy(
                        users = it.users + users, isLoading = false
                    )
                }

            }.onFailure {

                _state.update {
                    it.copy(
                        isLoading = false, errorMessage = it.errorMessage
                    )
                }
            }
        }
    }

    fun likeProfile(profile: User) {

        viewModelScope.launch {

            runCatching {

                likeProfileUseCase(profile.id)

            }.onSuccess { result ->

                when(result) {

                    is LikeProfileResult.Success -> {

                        _state.update {
                            it.copy(
                                snackbarMessage = "Лайк отправлен"
                            )
                        }
                    }

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
            }.onFailure(::handleReactionError)
        }
    }

    fun dislikeProfile(userId: String) {

        viewModelScope.launch {

            runCatching {

                feedRepository.dislikeProfile(userId)

            }.onSuccess {

                _state.update {

                    it.copy(
                        snackbarMessage = "Дизлайк отправлен",
                    )
                }

            }.onFailure(::handleReactionError)
        }
    }

    private fun handleReactionError(
        throwable: Throwable
    ) {

        if (throwable is ApiException) {

            if (throwable.statusCode == 409) {

                _state.update {
                    it.copy(
                        snackbarMessage = "Вы уже оценивали этого пользователя"
                    )
                }

                return
            }
        }

        _state.update {
            it.copy(
                snackbarMessage = throwable.message ?: "Ошибка"
            )
        }
    }

    fun snackbarShown() {

        _state.update {
            it.copy(
                snackbarMessage = null
            )
        }
    }

    fun profileScreenClosed() {

        _state.update {
            it.copy(
                closeProfileScreen = false
            )
        }
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

    fun selectUser(user: User) {
        feedRepository.setSelectedUser(user)
    }

    fun selectedUser(): User? {
        return feedRepository.getSelectedUser()
    }
}

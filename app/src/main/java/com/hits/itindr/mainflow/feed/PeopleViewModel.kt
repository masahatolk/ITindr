package com.hits.itindr.mainflow.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.core_network.ApiException
import com.hits.itindr.domain.usecase.LikeProfileUseCase
import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.profile.domain.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val repository: FeedRepository,
    private val likeProfileUseCase: LikeProfileUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PeopleUiState())

    val state = _state.asStateFlow()

    private var offset = 0

    private val limit = 20

    private var isEndReached = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {

        if (_state.value.isLoading) return

        if (isEndReached) return

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            runCatching {

                repository.getAllUsers(
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

    fun likeProfile(userId: String) {

        viewModelScope.launch {

            runCatching {

                likeProfileUseCase(userId)

            }.onSuccess { isMutual ->

                _state.update {

                    it.copy(
                        snackbarMessage = if (isMutual) "Ваши интерфейсы подошли друг другу"
                        else "Лайк отправлен",
                    )
                }

            }.onFailure(::handleReactionError)
        }
    }

    fun dislikeProfile(userId: String) {

        viewModelScope.launch {

            runCatching {

                repository.dislikeProfile(userId)

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
}
package com.hits.impl.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.api.repository.ProfileRepository
import com.hits.api.repository.TopicRepository
import com.hits.core_ui.TopicItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class EditProfileViewModel(
    private val profileRepository: ProfileRepository, private val topicRepository: TopicRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileUiState())

    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EditProfileEffect>()

    val effect = _effect.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            coroutineScope {

                val profileDeferred = async {
                    profileRepository.getProfile()
                }

                val topicsDeferred = async {
                    topicRepository.getTopics()
                }

                val profile = profileDeferred.await()

                val topics = topicsDeferred.await()

                _state.update { it ->
                    it.copy(
                        isLoading = false,

                        remoteAvatar = profile.avatar,

                        name = profile.name,

                        about = profile.about,

                        tags = topics.map {
                            TopicItem(
                                id = it.id, text = it.title
                            )
                        },

                        selectedIds = profile.topics.map { it.id }.toSet()
                    )
                }
            }
        }
    }

    fun onAvatarSelected(uri: Uri) {

        _state.update {
            it.copy(
                localAvatarUri = uri, avatarDeleted = false
            )
        }
    }

    fun onDeleteAvatarClick() {

        _state.update {

            if (it.remoteAvatar != null) {

                it.copy(
                    localAvatarUri = null,
                    avatarDeleted = true
                )

            } else {

                it.copy(
                    localAvatarUri = null,
                    avatarDeleted = false
                )
            }
        }
    }

    fun onNameChange(value: String) {
        _state.update {
            it.copy(name = value)
        }
    }

    fun onAboutChange(value: String) {
        _state.update {
            it.copy(about = value)
        }
    }

    fun onTopicsChanged(ids: Set<String>) {
        _state.update {
            it.copy(selectedIds = ids)
        }
    }

    fun onSaveClick() {

        viewModelScope.launch {

            val current = state.value

            runCatching {

                profileRepository.updateProfile(
                    name = current.name,
                    aboutMyself = current.about,
                    topics = current.selectedIds.toList()
                )

                when {

                    current.avatarDeleted -> {

                        profileRepository.deleteAvatar()
                    }

                    current.localAvatarUri != null -> {

                        profileRepository.uploadAvatar(
                            current.localAvatarUri.toString()
                        )
                    }
                }

            }.onSuccess {

                _effect.emit(
                    EditProfileEffect.Close
                )

            }.onFailure {
                // TODO показать ошибку
            }
        }
    }
}
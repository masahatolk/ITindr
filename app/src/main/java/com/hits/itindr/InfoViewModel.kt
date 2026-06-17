package com.hits.itindr

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.itindr.login.domain.AuthRepository
import com.hits.itindr.mainflow.profile.data.ProfileRepository
import com.hits.itindr.login.presentation.RegistrationStore
import com.hits.itindr.mainflow.profile.data.TopicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfoViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val topicRepository: TopicRepository,
    private val registrationStore: RegistrationStore
) : ViewModel() {

    private val _topics = MutableStateFlow<List<TagItem>>(emptyList())
    val topics = _topics.asStateFlow()

    fun saveProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                profileRepository.updateProfile(
                    name = name,
                    aboutMyself = aboutMyself,
                    topics = topics
                )

                onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("InfoViewModel", "Save profile error", e)
                onError()
            }
        }
    }

    fun loadTopics() {

        viewModelScope.launch {

            try {

                val topics = topicRepository.getTopics()

                _topics.value =
                    topics.map {
                        TagItem(
                            id = it.id,
                            text = it.title
                        )
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
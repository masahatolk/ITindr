package com.hits.itindr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.itindr.login.domain.AuthRepository
import com.hits.itindr.login.domain.ProfileRepository
import com.hits.itindr.login.presentation.RegistrationStore
import kotlinx.coroutines.launch

class InfoViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val registrationStore: RegistrationStore
) : ViewModel() {

    fun saveProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val registrationData =
                    registrationStore.registrationData.value
                        ?: return@launch

                authRepository.register(
                    email = registrationData.email,
                    password = registrationData.password
                )

                profileRepository.updateProfile(
                    name = name,
                    aboutMyself = aboutMyself,
                    topics = topics
                )

                onSuccess()

            } catch (e: Exception) {
                onError()
            }
        }
    }
}
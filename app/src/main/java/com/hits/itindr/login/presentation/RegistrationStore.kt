package com.hits.itindr.login.presentation

import com.hits.itindr.login.domain.RegistrationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationStore {

    private val _registrationData =
        MutableStateFlow<RegistrationData?>(null)

    val registrationData: StateFlow<RegistrationData?> =
        _registrationData

    fun saveCredentials(
        email: String,
        password: String
    ) {
        _registrationData.value =
            RegistrationData(
                email = email,
                password = password
            )
    }

    fun clear() {
        _registrationData.value = null
    }
}
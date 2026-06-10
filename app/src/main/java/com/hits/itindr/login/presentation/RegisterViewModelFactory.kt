package com.hits.itindr.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hits.itindr.login.domain.RegisterUseCase

class RegisterViewModelFactory(
    private val registerUseCase: RegisterUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(registerUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
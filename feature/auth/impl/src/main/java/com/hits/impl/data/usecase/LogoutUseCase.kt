package com.hits.impl.data.usecase

import com.hits.api.repository.AuthRepository

class LogoutUseCase (
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}
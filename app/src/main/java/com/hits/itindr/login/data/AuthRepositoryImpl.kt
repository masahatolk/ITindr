package com.hits.itindr.login.data

import com.hits.core_auth.TokenStore
import com.hits.core_auth.session.UserSession
import com.hits.itindr.login.domain.AuthRepository
import com.hits.itindr.mainflow.profile.data.ProfileRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val tokenStore: TokenStore,
    private val profileRepository: ProfileRepository,
    private val userSession: UserSession
) : AuthRepository {
    override suspend fun login(email: String, password: String) {
        val response = remoteDataSource.login(email, password)

        tokenStore.saveTokens(
            response.accessToken,
            response.refreshToken
        )

        val profile = profileRepository.getProfile()

        userSession.saveUserId(profile.id)
    }

    override suspend fun register(email: String, password: String) {
        val response = remoteDataSource.register(email, password)

        tokenStore.saveTokens(
            response.accessToken,
            response.refreshToken
        )

        val profile = profileRepository.getProfile()

        userSession.saveUserId(profile.id)
    }

    override suspend fun logout() {
        remoteDataSource.logout()
        tokenStore.clearToken()
        userSession.clear()
    }

    override suspend fun refresh(): Boolean {
        val refreshToken = tokenStore.getRefreshToken() ?: return false

        return try {
            val response = remoteDataSource.refresh(refreshToken)

            tokenStore.saveTokens(
                response.accessToken,
                response.refreshToken
            )
            true
        } catch (e: Exception) {
            tokenStore.clearToken()
            false
        }
    }
}
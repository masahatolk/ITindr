package com.hits.core_network.authenticator

import com.hits.core_auth.TokenStore
import com.hits.core_auth.data.AuthApi
import com.hits.core_auth.data.AuthRefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenStore: TokenStore,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(
        route: Route?,
        response: Response
    ): Request? {

        if (responseCount(response) >= 2) {
            return null
        }

        val refreshToken =
            tokenStore.getRefreshToken()
                ?: return null

        val refreshResponse = runBlocking {
            authApi.refresh(
                AuthRefreshRequest(
                    refreshToken
                )
            )
        }

        if (!refreshResponse.isSuccessful) {
            tokenStore.clearToken()
            return null
        }

        val body =
            refreshResponse.body()
                ?: return null

        tokenStore.saveTokens(
            accessToken = body.accessToken,
            refreshToken = body.refreshToken
        )

        return response.request
            .newBuilder()
            .header(
                "Authorization",
                "Bearer ${body.accessToken}"
            )
            .build()
    }

    private fun responseCount(
        response: Response
    ): Int {

        var current = response
        var result = 1

        while (current.priorResponse != null) {
            result++
            current = current.priorResponse!!
        }

        return result
    }
}
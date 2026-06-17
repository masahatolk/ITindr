package com.hits.core_network.interceptor

import com.hits.core_auth.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizedInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        if (response.code == 401) {
            tokenStore.clearToken()
        }

        return response
    }
}
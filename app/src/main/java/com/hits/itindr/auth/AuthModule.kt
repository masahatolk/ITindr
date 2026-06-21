package com.hits.itindr.auth

import com.hits.core_auth.TokenStore
import com.hits.core_auth.data.AuthApi
import com.hits.core_auth.session.UserSession
import com.hits.core_auth.session.UserSessionImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {

    single<TokenStore> {
        EncryptedTokenStore(get())
    }

    single<UserSession> {
        UserSessionImpl(get())
    }

    single<AuthApi> {
        get<Retrofit>(named("mainRetrofit"))
            .create(AuthApi::class.java)
    }

    single(named("refreshApi")) {
        get<Retrofit>(named("refreshRetrofit"))
            .create(AuthApi::class.java)
    }
}
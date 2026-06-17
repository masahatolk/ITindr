package com.hits.itindr.auth

import com.hits.core_auth.TokenStore
import com.hits.core_auth.data.AuthApi
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {

    single<TokenStore> {
        EncryptedTokenStore(get())
    }

    single<AuthApi> {
        get<Retrofit>().create(AuthApi::class.java)
    }
}
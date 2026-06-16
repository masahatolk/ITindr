package com.hits.itindr.auth

import com.hits.core_auth.TokenStore
import org.koin.dsl.module

val authModule = module {

    single<TokenStore> {
        EncryptedTokenStore(get())
    }

}
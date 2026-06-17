package com.hits.itindr

import android.app.Application
import com.hits.chat_feature.di.chatModule
import com.hits.core_network.di.networkModule
import com.hits.itindr.auth.authModule
import com.hits.itindr.login.di.loginModule
import com.hits.itindr.mainflow.feed.di.feedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                chatModule,
                authModule,
                networkModule,
                loginModule,
                feedModule
            )
        }
    }
}

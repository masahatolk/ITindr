package com.hits.itindr

import android.app.Application
import com.hits.chat_feature.di.chatModule
import com.hits.core_network.di.networkModule
import com.hits.itindr.auth.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        AppGraph.init(this)

        startKoin {
            androidContext(this@Application)
            modules(
                chatModule,
                authModule,
                networkModule
            )
        }
    }
}

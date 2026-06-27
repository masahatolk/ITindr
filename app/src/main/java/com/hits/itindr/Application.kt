package com.hits.itindr

import android.app.Application
import com.hits.impl.di.databaseModule
import com.hits.core_media.di.mediaModule
import com.hits.core_network.di.networkModule
import com.hits.impl.di.chatModule
import com.hits.impl.di.feedModule
import com.hits.impl.di.loginModule
import com.hits.impl.di.profileModule
import com.hits.impl.di.topicModule
import com.hits.itindr.auth.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                authModule,
                networkModule,
                databaseModule,
                loginModule,
                feedModule,
                profileModule,
                chatModule,
                mediaModule,
                topicModule,
            )
        }
    }
}

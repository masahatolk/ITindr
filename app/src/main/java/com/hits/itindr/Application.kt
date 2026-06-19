package com.hits.itindr

import android.app.Application
import com.hits.core_database.di.databaseModule
import com.hits.core_network.di.networkModule
import com.hits.impl.di.chatModule
import com.hits.itindr.auth.authModule
import com.hits.itindr.login.di.loginModule
import com.hits.itindr.mainflow.feed.di.feedModule
import com.hits.itindr.mainflow.profile.di.profileModule
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
                chatModule
            )
        }
    }
}

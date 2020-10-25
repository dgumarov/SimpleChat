package com.example.simplechat

import android.app.Application
import com.example.simplechat.utils.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App.applicationContext)
            androidLogger(Level.ERROR)
            modules(appModule)
        }
    }
}
package com.mobileprogramming.weather

import android.app.Application
import com.example.weatherreport.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import java.util.logging.Level


class Myapplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger(org.koin.core.logger.Level.ERROR)
            androidContext(this@Myapplication)
            modules(appModule)
        }
    }
}
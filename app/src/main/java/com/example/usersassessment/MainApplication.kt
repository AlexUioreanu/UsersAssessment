package com.example.usersassessment

import android.app.Application
import com.example.usersassessment.di.appModule
import com.example.usersassessment.di.dataModule
import com.example.usersassessment.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, networkModule, dataModule)
        }
    }
}
package com.example.fightingflow

import android.app.Application
import com.example.fightingflow.di.databaseModule
import com.example.fightingflow.di.repositoryModule
import com.example.fightingflow.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class FightingFlowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())

        startKoin {
            androidContext(this@FightingFlowApplication)
            modules(viewModelModule, databaseModule, repositoryModule)
            androidLogger()
        }
    }
}
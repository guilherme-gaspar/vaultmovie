package com.guilhermegaspar.vaultmovie.core

import android.app.Application
import com.guilhermegaspar.vaultmovie.di.KoinManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieApp)
            modules(KoinManager.loadAllModules())
        }
    }
}
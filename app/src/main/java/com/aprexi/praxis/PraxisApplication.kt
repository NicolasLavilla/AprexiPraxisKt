package com.aprexi.praxis

import android.app.Application
import com.aprexi.praxis.myapplication.di.baseModule
import com.aprexi.praxis.myapplication.di.praxisModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PraxisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PraxisApplication)
            modules(baseModule,praxisModule).allowOverride(true)
        }
    }
}
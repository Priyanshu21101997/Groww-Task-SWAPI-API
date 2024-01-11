package com.example.skeletonapplication

import com.example.skeletonapplication.di.AppModule
import com.example.skeletonapplication.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class StarWarsApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}


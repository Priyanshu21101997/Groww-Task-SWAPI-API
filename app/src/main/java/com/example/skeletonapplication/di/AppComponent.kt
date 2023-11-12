package com.example.skeletonapplication.di

import com.example.skeletonapplication.MyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules =
    [AppModule::class
    ,AndroidSupportInjectionModule::class
    , ActivityBuildersModule::class,
        FragmentBuildersModule::class])

interface AppComponent : AndroidInjector<MyApplication> {


}
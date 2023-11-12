package com.example.skeletonapplication.di

import android.content.Context
import androidx.room.Room
import com.example.skeletonapplication.MyApplication
import com.example.skeletonapplication.api.QuoteService
import com.example.skeletonapplication.db.QuoteDatabase
import com.example.skeletonapplication.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: MyApplication)  {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }

    @Singleton
    @Provides
    fun providesRoomDB(): QuoteDatabase {
        return Room.databaseBuilder(application,
            QuoteDatabase::class.java,
            "quoteDB")
            .build()
    }




}
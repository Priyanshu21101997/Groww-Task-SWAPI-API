package com.example.skeletonapplication.di

import androidx.room.Room
import com.example.skeletonapplication.StarWarsApplication
import com.example.skeletonapplication.api.StarWarsService
import com.example.skeletonapplication.db.StarWarsDatabase
import com.example.skeletonapplication.utils.AppConstants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: StarWarsApplication) {

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
    fun provideAPIService(retrofit: Retrofit): StarWarsService {
        return retrofit.create(StarWarsService::class.java)
    }

    @Singleton
    @Provides
    fun providesRoomDB(): StarWarsDatabase {
        return Room.databaseBuilder(
            application,
            StarWarsDatabase::class.java,
            "starWarsDB"
        )
            .build()
    }


}
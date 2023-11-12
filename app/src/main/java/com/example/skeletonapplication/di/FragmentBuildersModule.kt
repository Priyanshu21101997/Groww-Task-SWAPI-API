package com.example.skeletonapplication.di

import com.example.skeletonapplication.ui.DetailFragment
import com.example.skeletonapplication.ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributesDetailFragment(): DetailFragment
}
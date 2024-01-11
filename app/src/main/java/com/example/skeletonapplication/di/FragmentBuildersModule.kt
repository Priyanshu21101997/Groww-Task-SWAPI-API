package com.example.skeletonapplication.di

import com.example.skeletonapplication.ui.List.BottomSheet
import com.example.skeletonapplication.ui.detail.DetailFragment
import com.example.skeletonapplication.ui.List.ListFragment
import com.example.skeletonapplication.ui.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun contributesDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributesBottomSheetFragment(): BottomSheet

    @ContributesAndroidInjector
    abstract fun contributesSplashFragment(): SplashFragment
}
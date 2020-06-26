package com.weather.weather_forecast.di.module

import com.weather.weather_forecast.ui.view.fragments.DetailsFragment
import com.weather.weather_forecast.ui.view.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment
}
package com.weather.weather_forecast.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilderModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}
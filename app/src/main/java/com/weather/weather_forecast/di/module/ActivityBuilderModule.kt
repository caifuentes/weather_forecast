package com.weather.weather_forecast.di.module

import com.weather.weather_forecast.ui.view.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilderModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}
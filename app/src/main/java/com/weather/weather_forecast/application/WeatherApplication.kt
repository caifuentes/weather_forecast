package com.weather.weather_forecast.application

import android.app.Application
import com.weather.weather_forecast.di.utils.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WeatherApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any>  = androidInjector
}
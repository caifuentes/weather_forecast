package com.weather.weather_forecast.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weather.weather_forecast.R
import dagger.android.HasAndroidInjector

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
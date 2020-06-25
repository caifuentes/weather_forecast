package com.weather.weather_forecast.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cities(
    @SerializedName("cnt")
    @Expose
    val cnt: Int,
    @SerializedName("list")
    @Expose
    val list: MutableList<Details> = mutableListOf()
)
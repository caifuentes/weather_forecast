package com.weather.weather_forecast.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("weather")
    @Expose
    val weather: MutableList<Weather> = mutableListOf(),
    @SerializedName("main")
    @Expose
    val main: Main,
    @SerializedName("visibility")
    @Expose
    val visibility: Int,
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String
)
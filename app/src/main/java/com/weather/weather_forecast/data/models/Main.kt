package com.weather.weather_forecast.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    @Expose
    val temp: Double,
    @SerializedName("feels_like")
    @Expose
    val feelsLike: Double,
    @SerializedName("temp_min")
    @Expose
    val tempMin: Int,
    @SerializedName("temp_max")
    @Expose
    val tempMax: Int,
    @SerializedName("pressure")
    @Expose
    val pressure: Int,
    @SerializedName("humidity")
    @Expose
    val humidity: Int
)
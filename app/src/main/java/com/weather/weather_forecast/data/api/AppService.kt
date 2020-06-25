package com.weather.weather_forecast.data.api

import com.weather.weather_forecast.data.models.Cities
import com.weather.weather_forecast.data.models.Details
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val API_KEY = "344587a00b1676be5590988982c49ee7"
        const val DEFAULT_UNIT = "metric"
    }

    @GET("group/")
    suspend fun getCityList(@Query("id") id: String,
                            @Query("units") units: String,
                            @Query("appid") appId: String): Response<Cities>

    @GET("weather/")
    suspend fun getDetails(@Query("id") id: Int,
                           @Query("units") units: String,
                           @Query("appid") appId: String): Response<Details>
}
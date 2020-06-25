package com.weather.weather_forecast.data.repositories

import com.weather.weather_forecast.data.api.AppService
import com.weather.weather_forecast.data.api.BaseRepository
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val appService: AppService) : BaseRepository() {

    suspend fun getDetails(id: Int) = getResult { appService.getDetails(
        id, AppService.DEFAULT_UNIT, AppService.API_KEY
    )}
}
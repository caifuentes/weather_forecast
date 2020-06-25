package com.weather.weather_forecast.data.repositories

import com.weather.weather_forecast.data.api.AppService
import com.weather.weather_forecast.data.api.BaseRepository
import javax.inject.Inject

class ListRepository @Inject constructor(private val appService: AppService) : BaseRepository() {

    suspend fun getList(ids: String) = getResult { appService.getCityList(
        ids, AppService.DEFAULT_UNIT, AppService.API_KEY
    )}
}
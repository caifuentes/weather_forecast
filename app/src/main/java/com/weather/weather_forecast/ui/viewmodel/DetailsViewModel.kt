package com.weather.weather_forecast.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.weather.weather_forecast.data.repositories.DetailsRepository
import com.weather.weather_forecast.data.api.Result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: DetailsRepository) : ViewModel() {

    fun getDetails(id: Int) = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))

        try {
            emit(Result.success(data = repository.getDetails(id)))
        } catch (exception: Exception) {
            emit(Result.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun addToFavorites(id: Int) {

    }
}